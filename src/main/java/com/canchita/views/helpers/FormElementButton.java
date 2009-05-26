package com.canchita.views.helpers;

public class FormElementButton extends FormElement {
	
	
	public FormElementButton(String aType, String aName) {
		// TODO Auto-generated constructor stub
		super(aType,aName);
	}
	
	@Override
	public FormElementButton setValue(String value) {
		// TODO Auto-generated method stub
		super.setValue(value);
		
		return this;
	}
	
	@Override
	public FormElementButton setDecorator(Decorator deco) {
		// TODO Auto-generated method stub
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
