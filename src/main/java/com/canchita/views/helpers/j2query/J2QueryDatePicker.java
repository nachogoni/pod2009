package com.canchita.views.helpers.j2query;

public class J2QueryDatePicker extends J2QueryElement {
	private String id = "";
	private String openCallBack = "", closeCallBack = "";
	
	public J2QueryDatePicker(String elementId, String openCallBack, String closeCallBack) {
		id = elementId;
		this.openCallBack = openCallBack;
		this.closeCallBack = closeCallBack;
	}
	
	@Override
	public String getCode() {
		return String.format("$('#%s').datepicker($.extend( {beforeShow : %s,	onClose : %s,showMonthAfterYear : false,minDate : new Date()}, $.datepicker.regional['es']));", id, openCallBack, closeCallBack);
	}
	
	

}
