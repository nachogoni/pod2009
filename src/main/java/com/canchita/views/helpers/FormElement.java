package com.canchita.views.helpers;

import java.util.ArrayList;

public abstract class FormElement {
	protected String label;
	protected String type;
	protected String name;
	protected String value;
	protected Decorator deco;
	protected ArrayList<String> validators;
	private boolean required;
	

	public FormElement(String aType, String aName) {
		type = aType;
		name = aName;
		required = false;
		label = "";
		value = "";
		validators = new ArrayList<String>();
		deco = new Decorator();
	}
	
	public FormElement setRequired(boolean flag) {
		this.required = flag;
		return this;
	}
	
	public FormElement setLabel(String aLabel) {
		this.label = aLabel;
		return this;
	}
	
	protected String genLabel() {
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
	
	public FormElement addValidator(String aValidator){
		this.validators.add(aValidator);
		return this;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public FormElement setValue(String aValue) {
		this.value = aValue;
		
		return this;
	}
}

