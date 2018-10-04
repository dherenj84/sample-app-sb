package com.yourorg.sampleapp.service;

import org.springframework.core.io.FileSystemResource;

public interface ReportService {
	FileSystemResource toFile(String reportName, String format);
}
