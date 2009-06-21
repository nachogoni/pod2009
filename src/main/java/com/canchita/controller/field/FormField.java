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
		
		ArrayList<String> iplayers = new ArrayList<String>();
		
		for(int i=5;i<12;i++){
			iplayers.add(String.valueOf(i));
		}
		
		this.setName("Cancha")
  		.setMethod("post");

		this.addElement(new FormElementInput("text","name")
			.setLabel("Nombre")
			.setRequired(true)
			.addValidator("MaxLength", "50")
			.setMaxLength(50)
			.addValidator("IsAlphaNumS"));
		
		this.addElement(new FormElementInput("text","description")
			.setLabel("Descripción")
			.setRequired(false)
			.addValidator("MaxLength", "100")
			.setMaxLength(100)
			.addValidator("IsAlphaNumS"));
		
		this.addElement(new FormElementSelect("cantPlayers")
			.setLabel("Cantidad de Jugadores")
			.addValues(iplayers, iplayers));
		
		this.addElement(new FormElementInput("text","price")
			.setLabel("Precio")
			.setRequired(true)
			.addValidator("IsDecimal"));
		
		this.addElement(new FormElementSelect("hasRoof")
			.setLabel("Techada")
			.setRequired(false)
			.addValue("Sí", "true")
			.addValue("No", "false"));
		
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
        sprops.add("price");
        sprops.add("cantPlayers");
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
		
		aux = aField.getFloor().getValue();
		if (aux != null)
			dataPopu.put("floor", aux);
		
		aux = String.valueOf(aField.getPrice());
		if (aux != null)
			dataPopu.put("price", aux);
		
		aux = String.valueOf(aField.getNumberOfPlayers());
		if (aux != null)
			dataPopu.put("cantPlayers", aux);
		
		this.populate(dataPopu);
	}

}
