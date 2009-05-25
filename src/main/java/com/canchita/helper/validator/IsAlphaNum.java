package com.canchita.helper.validator;

public class IsAlphaNum extends Validator {

	private String space = "";

	public IsAlphaNum() {
		this(false);
	}
	
	public IsAlphaNum(boolean withSpaces) {
		if( withSpaces ) {
			this.space  = " ";
		}
	}

	@Override
	public boolean validate(String data) {
		
		return data.matches("[a-zA-ZñÑ0-9" + this.space + "]*");
	}
	
	@Override
	public String getError() {
		return "El valor debe ser alfanumerico";
	}

}
