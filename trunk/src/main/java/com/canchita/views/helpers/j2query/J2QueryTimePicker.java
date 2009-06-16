package com.canchita.views.helpers.j2query;

public class J2QueryTimePicker extends J2QueryElement {
	private String id = "";
	
	public J2QueryTimePicker(String elementId) {
		id = elementId;
	}
	
	@Override
	public String getCode() {
		return String.format("$(\"#%s\").timepickr();", id);
	}

}
