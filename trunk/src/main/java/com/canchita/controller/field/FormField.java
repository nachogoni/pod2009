package com.canchita.controller.field;

import java.util.ArrayList;
import java.util.HashMap;

import com.canchita.model.field.Field;
import com.canchita.model.field.FloorType;
import com.canchita.views.helpers.form.Decorator;
import com.canchita.views.helpers.form.FormElementButton;
import com.canchita.views.helpers.form.FormElementInput;
import com.canchita.views.helpers.form.FormElementSelect;
import com.canchita.views.helpers.form.FormHandler;

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
			.addValidator("IsAlphaNumS"));
		
		this.addElement(new FormElementInput("text","description")
			.setLabel("Descripción")
			.setRequired(false)
			.addValidator("IsAlphaNumS"));
		
		this.addElement(new FormElementSelect("hasRoof")
			.setLabel("Techada")
			.setRequired(false)
			.addValue("Sí", "True")
			.addValue("No", "False"));
		
		this.addElement(new FormElementSelect("floor")
			.setLabel("Piso")
			.setRequired(false)
			.addValue(FloorType.CONCRETE.toString(), "CONCRETE")
			.addValue(FloorType.ARTIFICIAL_GRASS.toString(), "ARTIFICIAL_GRASS")
			.addValue(FloorType.GRASS.toString(), "GRASS"));
		
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
        this.addDisplayGroup(sprops, "Características");
        
        sconfirm.add("submit");
		sconfirm.add("reset");
		this.addDisplayGroup(sconfirm, "Confirmación");
                
	}
	
	public FormField(Field aField) {
		this();

		String aux;
		HashMap<String, String> dataPopu = new HashMap<String, String>();
		
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

		this.populate(dataPopu);
	}

}
