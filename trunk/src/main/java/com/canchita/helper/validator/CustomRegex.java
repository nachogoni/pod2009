package com.canchita.helper.validator;

public class CustomRegex extends ValidatorWParam {
	private String regex;
	
	@Override
	public String getError() {
		return "El valor es incorrecto.";
	}

	@Override
	public boolean validate(String data) {
		if (regex.isEmpty())
			return false;
		
		return data.matches(regex);
	}
	
	public CustomRegex() {
		regex = "";
	}

	public CustomRegex(String aRegex) {
		this.regex = aRegex;
	}
	
	public void setParam(String param) {
		this.regex = param; 
	}
}
