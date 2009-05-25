package com.canchita.helper.validator;

public class IsNumeric extends Validator {

	@Override
	public String getError() {
		return "Debe ser numerico";
	}

	@Override
	public boolean validate(String data) {
		return data.matches("[0-9]*");
	}

}
