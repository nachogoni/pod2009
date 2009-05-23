package com.canchita.views.helpers.validators;

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
