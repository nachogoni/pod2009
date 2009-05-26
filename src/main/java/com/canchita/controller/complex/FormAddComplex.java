package com.canchita.controller.complex;

import com.canchita.views.helpers.Decorator;
import com.canchita.views.helpers.FormElement;
import com.canchita.views.helpers.FormHandler;

public class FormAddComplex extends FormHandler {

	public FormAddComplex() {
		super();
		
		/* armo el decorador */
		Decorator deco = new Decorator();
		deco.setFieldset("Complejo");
		
		this.setName("Complejo")
				  .setFormDecorator(deco)
			      .setMethod("post");
			      
		this.addElement(new FormElement("text","name")
						.setLabel("Nombre")
						.setRequired(true)
						.addValidator("IsAlphaNum"));
		
		this.addElement(new FormElement("text","description")
						.setLabel("Descripción")
						.setRequired(true));
		
		this.addElement(new FormElement("select","country")
						.setLabel("Pais")
						.addValue("lalal", "jeropa")
						.addValue("lalal2", "jeropa2"));
		
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
