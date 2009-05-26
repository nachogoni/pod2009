package com.canchita.views.helpers;

import java.util.ArrayList;

public class FormElement {
	private String label;
	private String type;
	private String name;
	private String value;
	private Decorator deco;
	protected ArrayList<String> validators;
	protected ArrayList<FormElementSelect> options;
	private boolean required;
	

	public FormElement(String aType, String aName) {
		type = aType;
		name = aName;
		required = false;
		label = "";
		value = "";
		validators = new ArrayList<String>();
		options = new ArrayList<FormElementSelect>();
		deco = new Decorator();
	}
	
	public FormElement addValue(String name, String Value){
		if (this.type == "select"){
			options.add(new FormElementSelect(name,Value));
		}
		return this;
	}

	public FormElement setRequired(boolean flag) {
		this.required = flag;
		return this;
	}
	
	public String toString() {
		String ret = "";
		
		if (this.type.equals("select"))
			ret = this.genLabel()+this.genSelect();
		else
			ret = this.genLabel()+this.genInput();
		
		if (!deco.getFieldset().isEmpty())
		{
			ret = String.format("<fieldset><legend> %s</legend>%s</fieldset>",
					this.name, ret);
		}
		
		return ret;
		
	}
	
	private String genInput() {
		return String.format("<input type=\"%s\" name=\"%s\" value=\"%s\">",
							this.type, this.name, this.value);
	}
	
	private String genSelect(){
		String ret = "";
		for (FormElementSelect e : options){
			ret += e.toString();
		}
		
		return String.format("<select name=\"%s\"> %s </select>", this.name, ret);
	}
	
	public FormElement setLabel(String aLabel) {
		this.label = aLabel;
		return this;
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
	
	public void setValue(String aValue) {
		this.value = aValue;
	}
}

