package com.canchita.views.helpers.j2query;

public class J2QueryMultipleData extends J2QueryElement {
	private String id, element, data;
	
	public J2QueryMultipleData(String idButton, String idElement, String adata) {
		id = idButton;
		element = idElement;
		data = adata;
	}
	
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return String.format("$(\"#%s\").click(function(){$(\"#%s\").append(\"%s\")});", id, element, data);
	}

}
