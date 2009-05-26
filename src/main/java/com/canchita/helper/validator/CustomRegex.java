package com.canchita.helper.validator;

public class CustomRegex extends Validator {

	private String regex;
	
	@Override
	public String getError() {
		return "El valor es incorrecto.";
	}

	@Override
	public boolean validate(String data) {
		// TODO Auto-generated method stub
		if (regex.isEmpty())
			return false;
		
		return data.matches(regex);
	}
	
	public CustomRegex() {
		// TODO Auto-generated constructor stub
		regex = "";
	}

	public CustomRegex(String aRegex) {
		// TODO Auto-generated constructor stub
		this.regex = aRegex;
	}
	
}
