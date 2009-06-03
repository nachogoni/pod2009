package com.canchita.helper.validator;

import org.apache.commons.validator.EmailValidator;

public class IsEMailAddress extends Validator {

	@Override
	public String getError() {
		return "La dirección de correo electrónico es inválida";
	}

	@Override
	public boolean validate(String data) {
		
		return EmailValidator.getInstance().isValid(data);
	}

}
