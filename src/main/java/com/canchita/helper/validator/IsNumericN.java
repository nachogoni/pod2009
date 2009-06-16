package com.canchita.helper.validator;

public class IsNumericN extends Validator {

	@Override
	public String getError() {
		return "Debe ser numérico.";
	}

	@Override
	public boolean validate(String data) {
		return data.matches("\\-?[0-9]*");
	}

}
