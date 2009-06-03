package com.canchita.views.helpers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class FormElement {
	protected String label;
	protected String type;
	protected String name;
	protected String value;
	protected String id;
	protected Decorator deco;
	protected Set<String> validators;
	protected Map<String, String> validatorValues;
	private boolean required;
	

	public FormElement(String aType, String aName) {
		type = aType;
		name = aName;
		required = false;
		label = "";
		value = "";
		id = "";
		validators = new HashSet<String>();
		validatorValues = new HashMap<String, String>();
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
	
	public FormElement setId(String aId) {
		this.id = aId;
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
	
	public FormElement addValidator(String aValidator, String aParam){
		this.validators.add(aValidator);
		this.validatorValues.put(aValidator, aParam);
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

	public FormElement setDecorator(Decorator deco) {
		this.deco = deco;
		
		return this;
	}
}

