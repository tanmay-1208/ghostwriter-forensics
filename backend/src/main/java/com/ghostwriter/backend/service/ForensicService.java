package com.ghostwriter.backend.service;

import com.ghostwriter.backend.model.ScanResult;
import com.ghostwriter.backend.repository.ScanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class ForensicService {

    @Autowired
    private ScanRepository scanRepository;

    public String analyzeCode(String targetCodePath) {
        StringBuilder output = new StringBuilder();
        try {
            // Setup the process to run your analyzer.py script
            List<String> command = new ArrayList<>();
            command.add("python3"); 
            File pythonScript = new File("../ghostwriter-ai/analyzer.py");
            command.add(pythonScript.getAbsolutePath());
            command.add(targetCodePath);

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true); // Combines error and standard output
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            
            // Filters output to ensure we only capture the JSON object
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.startsWith("{") && trimmed.endsWith("}")) {
                    output.append(trimmed);
                }
            }
            
            String finalJson = output.toString();

            if (!finalJson.isEmpty()) {
                saveToDatabase(targetCodePath, finalJson); // Log to H2 Database
                return finalJson;
            } else {
                return "{\"status\":\"error\", \"message\":\"No analysis data returned\"}";
            }

        } catch (Exception e) {
            return "{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}";
        }
    }

    private void saveToDatabase(String path, String json) {
        ScanResult record = new ScanResult();
        record.setFilename(new File(path).getName());
        
        // Dynamic Verdict Extraction
        if (json.contains("\"verdict\": \"Likely Human\"")) {
            record.setVerdict("Likely Human");
            record.setAiProbability("25%");
        } else if (json.contains("\"verdict\": \"Likely AI\"")) {
            record.setVerdict("Likely AI");
            record.setAiProbability("85%");
        } else {
            record.setVerdict("Analyzed");
            record.setAiProbability("50%");
        }

        record.setComplexity(8); // Placeholder for complexity metric
        scanRepository.save(record);
    }
}