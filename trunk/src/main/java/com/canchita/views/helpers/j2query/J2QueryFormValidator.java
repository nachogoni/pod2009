package com.canchita.views.helpers.j2query;

public class J2QueryFormValidator extends J2QueryElement {
	private String id = "";
	
	public J2QueryFormValidator(String formId) {
		id = formId;
	}
	@Override
	public String getCode() {
		return String.format("$(\"#%s\").validate();", id);
	}

}
