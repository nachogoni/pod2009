package com.canchita.helper.validator;

public class IsTelephone extends Validator {

	@Override
	public String getError() {
		return "El telefono debe ser de la forma (ccc) nnnn-nnnn.";
	}

	@Override
	public boolean validate(String data) {
		return data.matches("([0-9]{3})?[0-9]{4}\\-?[0-9]{4}");
	}

}

