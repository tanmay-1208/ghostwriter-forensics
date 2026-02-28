package com.ghostwriter.backend.repository;

import com.ghostwriter.backend.model.ScanResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Add this import

@Repository // Add this annotation to fix the "bean not found" error
public interface ScanRepository extends JpaRepository<ScanResult, Long> {
}