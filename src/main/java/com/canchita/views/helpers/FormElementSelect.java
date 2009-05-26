package com.canchita.views.helpers;

public class FormElementSelect {
	private String name;
	private String value;
	
	public FormElementSelect(String aName, String aValue) {
		name = aName;
		value = aValue;
	}
	
	public FormElementSelect() {
		// TODO Auto-generated constructor stub
		name = "";
		value = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return String.format("<option value=\"%s\">%s</option>",
				this.value, this.name);
	}
	
}
