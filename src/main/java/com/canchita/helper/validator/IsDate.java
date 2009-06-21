package com.canchita.helper.validator;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class IsDate extends Validator {
	private String regex;
	
	@Override
	public String getError() {
		return "Debe ser una fecha en formato dd/mm/yyyy.";
	}

	@Override
	public boolean validate(String data) {
		
		boolean valid = true;
		
		DateTimeFormatter parser = DateTimeFormat.forPattern("dd/MM/yyyy");

		try {
			parser.parseDateTime(data);
		} catch (Exception e) {
			valid = false;
		}
		
		return valid;
	}

}
