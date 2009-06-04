package com.canchita.helper.validator;

public class IsEMailAddress extends Validator {

	@Override
	public String getError() {
		return "La dirección de correo electrónico es inválida";
	}

	@Override
	public boolean validate(String data) {
		
		return true;
	}

}
