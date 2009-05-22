package com.canchita.views.helpers;

public class FormElement {
	private String label;
	private String type;
	private String name;
	private String value;
	private boolean required;
	

	public FormElement(String aType, String aName, boolean req, String aLabel,
			String aValue) {
		label = aLabel;
		type = aType;
		name = aName;
		required = req;
		value = aValue;
	}

	public String toString() {
		return this.genLabel()+this.genInput();
		
	}
	
	private String genInput() {
		return String.format("<input type=\"%s\" name=\"%s\" value=\"%s\">",
							this.type, this.name, this.value);
	}

	private String genLabel() {
		String nameFixed = this.label;
		if (this.required)
			nameFixed = "*" + this.label;

		if ( !this.label.equals("") )
			return String.format("<label for=\"%s\"> %s</label>",
					this.name, nameFixed);
		else
			return "";
	}

	public boolean isRequired() {
		return this.required;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setValue(String aValue) {
		this.value = aValue;
	}
}

