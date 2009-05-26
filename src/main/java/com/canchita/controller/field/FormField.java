package com.canchita.controller.field;

import java.util.ArrayList;
import java.util.HashMap;

import com.canchita.model.field.Field;
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
		
		this.addElement(new FormElementInput("hidden","idComplex"));

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
        sfield.add("idComplex");
        this.addDisplayGroup(sfield, "Cancha");
        
        sprops.add("hasRoof");
        sprops.add("floor");
        this.addDisplayGroup(sprops, "Caracteristicas");
        
        sconfirm.add("submit");
		sconfirm.add("reset");
		this.addDisplayGroup(sconfirm, "Confirmacion");
                
	}
	
	public FormField(Field aField) {
		this();

		String aux;
		HashMap<String, String> dataPopu = new HashMap<String, String>();
		
		ArrayList<String> sfield = new ArrayList<String>();
		ArrayList<String> sprops = new ArrayList<String>();
		ArrayList<String> sconfirm = new ArrayList<String>();
		
		aux = aField.getName();
		if (aux != null)
			dataPopu.put("name", aux);
		
		aux = aField.getDescription();
		if (aux != null)
			dataPopu.put("description", aux);
		
		aux = ((Boolean)aField.isHasRoof()).toString();
		if (aux != null)
			dataPopu.put("hasRoof", aux);
		
		aux = aField.getFloor().toString();
		if (aux != null)
			dataPopu.put("floor", aux);
		
		sfield.add("name");
		sfield.add("description");
		this.addDisplayGroup(sfield, "Cancha");
		
		sprops.add("hasRoof");
		sprops.add("floor");
		this.addDisplayGroup(sprops, "Caracteristicas");
		
		sconfirm.add("submit");
		sconfirm.add("reset");
		this.addDisplayGroup(sconfirm, "Confirmacion");

		this.populate(dataPopu);
	}

}
