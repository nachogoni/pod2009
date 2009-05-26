package com.canchita.views.helpers;

import java.util.ArrayList;

public class FormElementSelect extends FormElement {

	protected ArrayList<FormElementSelectValue> options;

	public FormElementSelect(String name) {
		super("select", name);
		options = new ArrayList<FormElementSelectValue>();

	}

	public FormElementSelect addValue(String name, String Value) {
		options.add(new FormElementSelectValue(name, Value));

		return this;
	}

	public String toString() {
		String ret = "";
		
		for (FormElementSelectValue e : options) {
			ret += e.toString();
		}

		return String.format("%s<select name=\"%s\"> %s </select>", 
				super.genLabel(), super.getName(), ret);
	}
	
	@Override
	public FormElementSelect setValue(String value) {
		// TODO Auto-generated method stub
		super.setValue(value);
		
		return this;
	}
	
	@Override
	public FormElementSelect setLabel(String label) {
		// TODO Auto-generated method stub
		super.setLabel(label);
		
		return this;
	}
	
	@Override
	public FormElementSelect setRequired(boolean flag) {
		// TODO Auto-generated method stub
		super.setRequired(flag);
		
		return this;
	}
	
	
	@Override
	public FormElementSelect addValidator(String validator) {
		// TODO Auto-generated method stub
		super.addValidator(validator);
		
		return this;
	}

}
