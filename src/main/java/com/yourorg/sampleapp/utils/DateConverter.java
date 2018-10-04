package com.yourorg.sampleapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class DateConverter implements AttributeConverter<String, Date> {

	Logger log = LoggerFactory.getLogger(this.getClass());

	private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	@Override
	public Date convertToDatabaseColumn(String attribute) {
		if (!StringUtils.isEmpty(attribute))
			try {
				return sdf.parse(attribute);
			} catch (ParseException e) {
				log.error("error converting string to data.Exception is---->", e);
			}
		return null;
	}

	@Override
	public String convertToEntityAttribute(Date dbData) {
		if (dbData != null)
			return sdf.format(dbData);
		return "";
	}

}
