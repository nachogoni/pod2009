package com.canchita.helper.validator;

public class IsPercentage extends Validator {

	@Override
	public String getError() {
		return "Debe ser un número entre 0 y 1.";
	}

	@Override
	public boolean validate(String data) {
		return data.matches("[0]") || data.matches("[1]")
				|| data.matches("1\\.[0]*")
				|| data.matches("0\\.[0]*")
				|| data.matches("[0]\\.[0-9]*");
	}

}
