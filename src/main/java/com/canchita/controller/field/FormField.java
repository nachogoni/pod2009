package com.canchita.controller.field;

import java.util.ArrayList;

import com.canchita.model.booking.Expiration;
import com.canchita.model.field.FloorType;
import com.canchita.views.helpers.Decorator;
import com.canchita.views.helpers.FormElementButton;
import com.canchita.views.helpers.FormElementInput;
import com.canchita.views.helpers.FormElementSelect;
import com.canchita.views.helpers.FormHandler;

public class FormField extends FormHandler {

	public FormField() {
		ArrayList<String> sfield = new ArrayList<String>();
		ArrayList<String> sprops = new ArrayList<String>();
		ArrayList<String> sconfirm = new ArrayList<String>();
		
		this.setName("Cancha")
  		.setMethod("post");

		this.addElement(new FormElementInput("text","name")
			.setLabel("Nombre")
			.setRequired(true)
			.addValidator("IsAlphaNum"));
		
		this.addElement(new FormElementInput("text","description")
			.setLabel("Descripción")
			.setRequired(false)
			.addValidator("IsAlphaNum"));
		
		this.addElement(new FormElementSelect("hasRoof")
			.setLabel("Techada")
			.setRequired(true)
			.addValue("Sí", "True")
			.addValue("No", "False"));
		
		this.addElement(new FormElementSelect("floor")
			.setLabel("Piso")
			.setRequired(true)
			.addValue("Cemento", "CONCRETE")
			.addValue("Césped Sintético", "ARTIFICIAL_GRASS")
			.addValue("Césped", "GRASS"));
		
		Decorator decorator = new Decorator()
			.setSclass("submit-go"); 
		
		this.addElement(new FormElementButton("submit","submit")
			.setValue("Confirmar")
			.setDecorator(decorator));
	
		this.addElement(new FormElementButton("reset","reset")
			.setValue("Reset")
			.setDecorator(decorator));
        
        sfield.add("name");
        sfield.add("description");
        this.addDisplayGroup(sfield, "Cancha");
        
        sprops.add("hasRoof");
        sprops.add("floor");
        this.addDisplayGroup(sprops, "Caracteristicas");
        
        sconfirm.add("submit");
		sconfirm.add("reset");
		this.addDisplayGroup(sconfirm, "Confirmacion");
	}

}
