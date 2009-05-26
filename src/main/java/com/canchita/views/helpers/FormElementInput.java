package com.canchita.views.helpers;

public class FormElementInput extends FormElement {

	public FormElementInput(String type, String name) {
		super(type, name);
		// TODO Auto-generated constructor stub
	}

	protected String genInput() {
		return String.format("<input type=\"%s\" name=\"%s\" value=\"%s\">",
							super.type, super.name, super.value);
	}
	
	@Override
	public FormElementInput setValue(String value) {
		// TODO Auto-generated method stub
		super.setValue(value);
		
		return this;
	}

	public String toString() {
		String ret = "";
		
		ret = super.genLabel()+this.genInput();
		
		if (!super.deco.getFieldset().isEmpty())
		{
			ret = String.format("<fieldset><legend> %s</legend>%s</fieldset>",
					this.name, ret);
		}
		
		return ret;
		
	}
	
}
