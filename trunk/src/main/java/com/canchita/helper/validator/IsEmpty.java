package com.canchita.helper.validator;

public class IsEmpty extends Validator {

	@Override
	public String getError() {
		// TODO Auto-generated method stub
		return "Debe contener algún valor.";
	}

	@Override
	public boolean validate(String data) {
		// TODO Auto-generated method stub
		return !data.isEmpty();
	}

}
