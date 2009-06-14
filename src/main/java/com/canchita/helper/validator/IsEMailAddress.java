package com.canchita.helper.validator;

public class IsEMailAddress extends Validator {

	@Override
	public String getError() {
		return "La dirección de correo electrónico es inválida";
	}

	@Override
	public boolean validate(String data) {
		return data.matches("^([0-9a-zA-Z]+[-._+&amp;])*[0-9a-zA-Z]+@"
				+"([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
	}

}
