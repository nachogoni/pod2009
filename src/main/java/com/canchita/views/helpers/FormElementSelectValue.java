package com.canchita.views.helpers;

public class FormElementSelectValue {
	private String name;
	private String value;
	private boolean isdefault; 
	
	public FormElementSelectValue(String aName, String aValue) {
		name = aName;
		value = aValue;
		isdefault = false;
	}
	
	public FormElementSelectValue(String aName, String aValue, boolean adefault) {
		name = aName;
		value = aValue;
		isdefault = adefault;
	}
	
	public FormElementSelectValue() {
		name = "";
		value = "";
		isdefault = false;
	}
	
	public String toString() {
		return String.format("<option value=\"%s\"%s>%s</option>",
				this.value, (this.isdefault == true) ? " selected" : "" ,this.name);
	}

}
