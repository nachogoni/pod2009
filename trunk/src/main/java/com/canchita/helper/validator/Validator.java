package com.canchita.helper.validator;

public abstract class  Validator {
	public abstract boolean validate(String data);
	
	public abstract String getError();
}
