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
		if (!data.matches("[0-2][0-9]:[0-5][0-9]")) {
			return false;
		}

		String[] hoursAndMins = data.split(":");

		return Integer.parseInt(hoursAndMins[0]) <= 23
				&& Integer.parseInt(hoursAndMins[1]) <= 59;
	}

}
