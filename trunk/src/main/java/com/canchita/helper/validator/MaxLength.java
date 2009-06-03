package com.canchita.helper.validator;

public class MaxLength extends ValidatorWParam {

	int maxSize = 0;
	
	public void setParam(String param) {
		this.maxSize = Integer.parseInt(param);
	}

	public String getError() {
		return String.format("El dato no debe superar los %s caracteres", this.maxSize);
	}

	public boolean validate(String data) {
		return (data.length() <= this.maxSize) ;
	}

}
