package com.canchita.views.helpers.validators;

public class IsAlphaNum extends Validator {

	@Override
	public boolean validate(String data) {
		return data.matches("[a-zA-ZñÑ0-9]*");
	}
	
	@Override
	public String getError() {
		return "El valor debe ser alfanumerico";
	}

}
