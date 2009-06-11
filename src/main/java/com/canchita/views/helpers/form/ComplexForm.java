package com.canchita.views.helpers.form;

public class ComplexForm extends FormHandler {
	
	public ComplexForm() {
		super();
		Decorator deco = new Decorator();
		
		deco.setFieldset("Prueba");
		
		this.setMethod("post");
		this.setName("ComplexForm");
		this.setFormDecorator(deco);
		
		//this.setAttribute("name", "ComplexForm");

		this.addElement(new FormElementInput("text", "name"))
			.setRequired(true)
			.setLabel("Nombre:")
			.addValidator("IsAlpha")
			.setValue("defaultValue");
		
		this.addElement(new FormElementInput("text", "descripcion"))
			.setRequired(true)
			.addValidator("IsAlpha")
			.setLabel("Descripcion:");

	}

}
