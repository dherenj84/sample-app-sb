package com.yourorg.sampleapp.service;

import java.util.List;

import org.springframework.core.io.FileSystemResource;

import com.yourorg.sampleapp.utils.ExcelReportWriter;
import com.yourorg.sampleapp.utils.ReportColumn;

public abstract class ExcelReportService implements ReportService {

	@Override
	public abstract FileSystemResource toFile(String reportName, String format);

	protected <T extends Object> FileSystemResource toExcelFile(String reportName, List<T> data,
			List<ReportColumn> columns) {
		return ExcelReportWriter.toFile(reportName, data, columns);
	}

}
