package com.canchita.views.helpers;

public class ComplexForm extends FormHandler {
	
	public ComplexForm() {
		super();

		this.setMethod("post");
		this.setAttribute("name", "ComplexForm");

		this.addElement(new FormElement("text", "name"))
			.setRequired(true)
			.setLabel("Nombre:")
			.setValue("defaultValue");
		
		this.addElement(new FormElement("text", "lastname"))
			.setRequired(true)
			.setLabel("Apellido:");

	}
	
	public boolean isValid() {
		
		boolean ret = true;
		FormElement e;
		
		/*Name validations*/
		e = this.formValues.get("name");
		if (e.isRequired() && e.getValue().isEmpty()) {
			this.errors.put(e.getName(), "Este campo no puede estar vacío.");
			ret = false;
		}
		
		/*Lastname validations*/
		e = this.formValues.get("lastname");
		if (!e.getValue().matches("[a-zA-ZñÑ]*")) {
			this.errors.put(e.getName(), "Este campo sólo permite letras.");
			ret = false;
		}
		
		return ret;
	}

}
