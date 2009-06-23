package com.canchita.views.helpers.form;

public class FormElementButton extends FormElement {
	
	
	public FormElementButton(String aType, String aName) {
		super(aType,aName);
	}
	
	@Override
	public FormElementButton setValue(String value) {
		super.setValue(value);
		
		return this;
	}
	
	@Override
	public FormElementButton setDecorator(Decorator deco) {
		super.setDecorator(deco);
		
		return this;
	}
	
	public String toString() {
		String strclass = "";
		
		if (!this.deco.getSclass().equals("")){
			strclass = String.format(" class=\"%s\"", this.deco.getSclass());
		}
		
		return String.format("<input id=\"%s\" type=\"%s\" name=\"%s\" value=\"%s\" %s>",
							super.id, super.type, super.name, super.value, strclass);
	}
	
}
