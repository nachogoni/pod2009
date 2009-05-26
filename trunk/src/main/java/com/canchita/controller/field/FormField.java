package com.canchita.controller.field;

import java.util.ArrayList;
import java.util.HashMap;

import com.canchita.model.field.Field;
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
			.setLabel("Techadaaa")
			.setRequired(false)
			.addValue("Sí", (new Boolean(true)).toString())
			.addValue("No", (new Boolean(false)).toString()));
		
		this.addElement(new FormElementSelect("floor")
			.setLabel("Piso")
			.setRequired(false)
			.addValue(FloorType.CONCRETE.toString(), FloorType.CONCRETE.toString())
			.addValue(FloorType.ARTIFICIAL_GRASS.toString(), FloorType.ARTIFICIAL_GRASS.toString())
			.addValue(FloorType.GRASS.toString(), FloorType.GRASS.toString()));
		
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
