package com.canchita.helper.validator;

public class IsDecimal extends Validator {

	@Override
	public String getError() {
		return "Debe ser decimal.";
	}

	@Override
	public boolean validate(String data) {
		return data.matches("[0-9]+.[0-9]+");
	}

}
