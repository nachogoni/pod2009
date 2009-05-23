package com.canchita.views.helpers;

public class ComplexForm extends FormHandler {
	
	public ComplexForm() {
		super();

		this.setMethod("post");
		this.setAttribute("name", "ComplexForm");

		this.addElement(new FormElement("text", "name"))
			.setRequired(true)
			.setLabel("Nombre:")
			.addValidator("isAlpha")
			.setValue("defaultValue");
		
		this.addElement(new FormElement("text", "lastname"))
			.setRequired(true)
//			.addValidator("isAlpha")
			.setLabel("Apellido:");

	}

}
