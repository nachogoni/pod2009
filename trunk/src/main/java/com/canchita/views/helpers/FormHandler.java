package com.canchita.views.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class FormHandler {
	private ArrayList<FormElement> formElements;
	private HashMap<String, FormElement> formValues;
	private HashMap<String, String> errors;

	public FormHandler() {
		errors = new HashMap<String, String>();
		formValues = new HashMap<String, FormElement>();
		formElements = new ArrayList<FormElement>();

		formElements.add(new FormElement("text", "name", true,
				"Nombre:", ""));
		formElements.add(new FormElement("text", "lastname", true,
				"Apellido:", ""));

		for (FormElement e : formElements) {
			formValues.put(e.getName(), e);
		}
		
	}
	
	public String toString() {

		String ret;
		String err;
		
		ret = "<form action=\"\" method=\"post\">";
		
		for (FormElement e : formElements) {
			ret += e;
			if ((err = this.errors.get(e.getName())) != null)
				ret += err;
		}
			
		ret += "<input type=\"submit\" value=\"Agregar\">";
		ret += "</form>";
		
		return ret;
	}

	public void loadValues(HttpServletRequest request) {
		for (FormElement e : this.formElements) {
			e.setValue(request.getParameter(e.getName()));
		}
			
	}

	public boolean validate() {
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
