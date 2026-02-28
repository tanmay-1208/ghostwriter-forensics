package com.ghostwriter.backend.controller;

import com.ghostwriter.backend.model.ScanResult;
import com.ghostwriter.backend.repository.ScanRepository;
import com.ghostwriter.backend.service.ForensicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/forensics")
@CrossOrigin(origins = "http://localhost:3000") 
public class ForensicController {

    @Autowired
    private ForensicService forensicService;

    @Autowired
    private ScanRepository scanRepository;

    @GetMapping("/analyze")
    public String analyze(@RequestParam String path) {
        return forensicService.analyzeCode(path);
    }

    @GetMapping("/history")
    public List<ScanResult> getHistory() {
        return scanRepository.findAll();
    }
}