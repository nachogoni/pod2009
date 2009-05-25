package com.canchita.helper.validator;

public class IsAlpha extends Validator {

	@Override
	public boolean validate(String data) {
		// TODO Auto-generated method stub
		return data.matches("[a-zA-ZñÑ]*");
	}
	
	@Override
	public String getError() {
		return "El valor debe ser alfabetico unicamente";
	}

}
