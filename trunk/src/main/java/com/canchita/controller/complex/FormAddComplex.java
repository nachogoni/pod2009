package com.canchita.controller.complex;

import java.util.ArrayList;

import com.canchita.views.helpers.Decorator;
import com.canchita.views.helpers.FormElementInclude;
import com.canchita.views.helpers.FormElementInput;
import com.canchita.views.helpers.FormElementSelect;
import com.canchita.views.helpers.FormHandler;

public class FormAddComplex extends FormHandler {

	public FormAddComplex() {
		super();
		ArrayList<String> elements = new ArrayList<String>();

		/* armo el decorador */
		Decorator deco = new Decorator();
		deco.setFieldset("Complejo");
		
		this.setName("Complejo")
	  		.setFormDecorator(deco)
	  		.setMethod("post");
			      
		this.addElement(new FormElementInput("text","name")
			.setLabel("Nombre")
			.setRequired(true)
			.addValidator("IsAlphaNum"));

		this.addElement(new FormElementInput("text","description")
			.setLabel("Descripción")
			.setRequired(true));
		
		this.addElement(new FormElementSelect("country")
						.setLabel("selectlabel")
						.addValue("sarasa", "sarasa"));
		
		this.addElement(new FormElementInclude("/WEB-INF/resources/countries_es.html"));

		elements.add("name");
		elements.add("/WEB-INF/resources/countries_es.html");
		this.addDisplayGroup(elements, "posta");
		
		elements.add("description");
		this.addDisplayGroup(elements, "posta2");
		elements.add("country");
		this.addDisplayGroup(elements, "posta3");
		
		/*
		this.addElement(new FormElement("text","name")
						.setLabel("Direccion")
						.setRequired(true)
						.addValidator("IsAlphaNum"));
		
		this.addElement(new FormElement("text","name")
						.setLabel("Barrio")
						.setRequired(true)
						.addValidator("IsAlphaNum"));
		
		this.addElement(new FormElement("text","Ciudad")
						.setLabel("Nombre")
						.setRequired(true)
						.addValidator("IsAlphaNum"));
		
		this.addElement(new FormElement("text","name")
						.setLabel("Codigo Postal")
						.setRequired(true)
						.addValidator("IsAlphaNum"));
		
		this.addElement(new FormElement("text","name")
						.setLabel("Latitud"));
		
		this.addElement(new FormElement("text","name")
						.setLabel("Longitud"));
		
		this.addElement(new FormElement("text","name")
						.setLabel("Telefono"));
		
		this.addElement(new FormElement("text","name")
						.setLabel("Pais"));
		*/
	}
}
