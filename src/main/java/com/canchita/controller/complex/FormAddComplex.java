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
						.setLabel("Descripción"));
	}
}
