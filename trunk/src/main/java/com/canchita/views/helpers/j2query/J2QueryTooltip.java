package com.canchita.views.helpers.j2query;

public class J2QueryTooltip extends J2QueryElement {
	private String id = null, tooltip = null;
	
	public J2QueryTooltip(String aId, String aTooltip) {
		id = aId;
		tooltip = aTooltip;
	}
	
	public String getCode(){
		return String.format("setTooltip('#%s','%s');", id, tooltip);
	}
}
