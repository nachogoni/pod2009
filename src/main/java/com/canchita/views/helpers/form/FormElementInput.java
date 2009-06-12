package com.canchita.views.helpers.form;

import com.canchita.views.helpers.j2query.J2QueryMultipleData;

public class FormElementInput extends FormElement {
	protected boolean multipleData;
	protected String idButton;
	
	public FormElementInput(String type, String name) {
		super(type, name);
		multipleData = false;
	}

	public boolean isMultipleData(){
		return multipleData;
	}

	@Override
	public FormElementInput setId(String id) {
		super.setId(id);
		
		return this;
	}
	
	public FormElementInput setMultipleData(String idBoton){
		multipleData = true;
		idButton = idBoton;
		
		return this;
	}
	
	@Override
	public FormElementInput addValidator(String validator, String param) {
		super.addValidator(validator, param);
		
		return this;
	}

	protected String genInput() {
		String strclass = "";
		
		if (!this.deco.getSclass().equals("")){
			strclass = String.format(" class=\"%s\"", this.deco.getSclass());
		}
		
		return String.format("<div id='div%s'><input id='%s' type='%s' name='%s' value='%s' %s></div>",
							super.name, super.id,super.type, super.name, super.value, strclass);
	}
	
	@Override
	public FormElementInput setValue(String value) {
		// TODO Auto-generated method stub
		super.setValue(value);
		
		return this;
	}
	
	@Override
	public FormElementInput setLabel(String label) {
		super.setLabel(label);
		
		return this;
	}
	
	@Override
	public FormElementInput setRequired(boolean flag) {
		super.setRequired(flag);

		return this;
	}
	
	@Override
	public FormElementInput addValidator(String validator) {
		// TODO Auto-generated method stub
		super.addValidator(validator);
		
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
		
		//cuando hace el toString si esta habilitado el multiData agrego el JS
		if (multipleData){
			this.addJJQueryElement(new J2QueryMultipleData(this.idButton,"div" + this.name, ret));
			ret += String.format("<div id='%s' style='position:relative; top:-2em; left:23em;'>+</div>",this.idButton);
		}
		return ret;
		
	}
	
	@Override
	public FormElementInput setDecorator(Decorator deco) {
		// TODO Auto-generated method stub
		super.setDecorator(deco);
		
		return this;
	}
	
}
