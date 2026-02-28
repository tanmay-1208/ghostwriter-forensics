package com.ghostwriter.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ScanResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String filename;
    private String verdict;
    private Integer complexity;
    private String aiProbability;
    private LocalDateTime scanDate = LocalDateTime.now();

    // Manually added Setters to fix your compilation error
    public void setFilename(String filename) { this.filename = filename; }
    public void setVerdict(String verdict) { this.verdict = verdict; }
    public void setComplexity(Integer complexity) { this.complexity = complexity; }
    public void setAiProbability(String aiProbability) { this.aiProbability = aiProbability; }

    // Standard Getters (needed for the History table later)
    public Long getId() { return id; }
    public String getFilename() { return filename; }
    public String getVerdict() { return verdict; }
    public Integer getComplexity() { return complexity; }
    public String getAiProbability() { return aiProbability; }
    public LocalDateTime getScanDate() { return scanDate; }
}