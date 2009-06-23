package com.canchita.helper.validator;

public class IsEmpty extends Validator {

	@Override
	public String getError() {
		return "Debe contener algún valor.";
	}

	@Override
	public boolean validate(String data) {
		if (data == null)
			return false;
		
		return !data.isEmpty();
	}

}
