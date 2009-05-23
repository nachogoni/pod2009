package com.canchita.views.helpers.validators;

public abstract class  Validator {
	public abstract boolean validate(String data);
	
	public abstract String getError();
}
