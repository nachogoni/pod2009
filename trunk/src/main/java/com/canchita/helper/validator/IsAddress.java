package com.canchita.helper.validator;

public class IsAddress extends Validator {

	@Override
	public String getError() {
		return "La direccion es incorrecta";
	}

	@Override
	public boolean validate(String data) {
		return data.matches("[a-zA-ZñÑ ]+[a-zA-ZñÑ 0-9]*");
	}

}
