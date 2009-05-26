package com.canchita.helper.validator;

public class IsAlphaNumS extends Validator {

	@Override
	public boolean validate(String data) {
		
		return data.matches("[a-zA-ZñÑ0-9 ]*");
	}
	
	@Override
	public String getError() {
		return "El valor debe ser alfanumérico.";
	}

}
