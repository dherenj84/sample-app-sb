package com.yourorg.sampleapp.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;

public class ExcelReportWriter {
	private static Logger log = LoggerFactory.getLogger(ExcelReportWriter.class);

	private static ExecutorService executor = Executors.newCachedThreadPool();

	public static <T extends Object> FileSystemResource toFile(String reportName, List<T> data,
			List<ReportColumn> columns) {
		FileSystemResource file = null;
		try {
			Workbook workbook = new XSSFWorkbook();
			CreationHelper createHelper = workbook.getCreationHelper();
			// Create a Sheet
			Sheet sheet = workbook.createSheet("report");

			// Create a Font for styling header cells
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 14);
			headerFont.setColor(IndexedColors.RED.getIndex());

			// Create a CellStyle with the font
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			// Create a Row
			Row headerRow = sheet.createRow(0);

			for (int i = 0; i < columns.size(); i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns.get(i).getLabel());
				cell.setCellStyle(headerCellStyle);
			}

			// Create Cell Style for formatting Date
			CellStyle dateCellStyle = workbook.createCellStyle();
			dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("MM/dd/yyyy"));

			// Create Other rows and cells with employees data
			int rowNum = 1;
			for (Object rowData : data) {
				Row row = sheet.createRow(rowNum++);
				int colNum = 0;
				for (ReportColumn column : columns) {
					row.createCell(colNum++)
							.setCellValue((String) PropertyUtils.getProperty(rowData, column.getField()));
				}
			}

			// Resize all columns to fit the content size
			for (int i = 0; i < columns.size(); i++) {
				sheet.autoSizeColumn(i);
			}

			File tmp = File.createTempFile(reportName, ".xlsx");
			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(tmp);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			file = new FileSystemResource(tmp);
			executor.execute(() -> {
				try {
					Thread.sleep(100l);
					tmp.delete();
				} catch (Exception e) {
					log.error("error deleting temp file", e);
				}
			});
		} catch (Exception e) {
			log.error("error creating csv file. Exception is---->", e);
		}
		return file;
	}
}
