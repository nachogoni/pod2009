package com.canchita.helper.validator;

public class MinLength extends ValidatorWParam {

	int minSize = 0;
	
	public void setParam(String param) {
		this.minSize = Integer.parseInt(param);
	}

	public String getError() {
		return String.format("El dato debe contener al menos %s caracteres", this.minSize);
	}

	public boolean validate(String data) {
		return (data.length() >= this.minSize) ;
	}

}
