package com.canchita.helper.validator;

public class IsAlphaS extends Validator {

	@Override
	public boolean validate(String data) {
		return data.matches("[a-zA-ZñÑ ]*");
	}
	
	@Override
	public String getError() {
		return "El valor debe ser alfabético únicamente.";
	}

}
