package com.canchita.views.helpers.form;

public class FormElementInput extends FormElement {

	public FormElementInput(String type, String name) {
		super(type, name);
		// TODO Auto-generated constructor stub
	}

	protected String genInput() {
		String strclass = "";
		
		if (!this.deco.getSclass().equals("")){
			strclass = String.format(" class=\"%s\"", this.deco.getSclass());
		}
		
		return String.format("<div id='div%s'><input id=\"%s\" type=\"%s\" name=\"%s\" value=\"%s\" %s></div>",
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
		
		return ret;
		
	}
	
	@Override
	public FormElementInput setDecorator(Decorator deco) {
		// TODO Auto-generated method stub
		super.setDecorator(deco);
		
		return this;
	}
	
}
