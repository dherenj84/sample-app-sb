package com.yourorg.sampleapp.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportColumn {
	private String label;
	private String field;
	private String type;
}
