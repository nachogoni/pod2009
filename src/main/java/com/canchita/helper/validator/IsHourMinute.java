package com.canchita.helper.validator;

public class IsHourMinute extends ValidatorWParam {

	private String msg;

	public void setParam(String param) {
		this.msg = param;
	}
	@Override
	public String getError() {
		return String.format("%s erróneo. Debe ser hh:mm.", this.msg);
	}

	@Override
	public boolean validate(String data) {
		return data.matches("[0-5][0-9]:[0-5][0-9]");
	}

}
