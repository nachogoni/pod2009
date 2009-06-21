package com.canchita.controller.field;

import java.util.ArrayList;

import com.canchita.model.field.FloorType;
import com.canchita.views.helpers.form.Decorator;
import com.canchita.views.helpers.form.FormElementButton;
import com.canchita.views.helpers.form.FormElementInput;
import com.canchita.views.helpers.form.FormElementSelect;
import com.canchita.views.helpers.form.FormHandler;

public class SearchFieldForm extends FormHandler {

	public SearchFieldForm() {
		ArrayList<String> sfield = new ArrayList<String>();

		this.setName("Búsqueda de canchas")
  			.setMethod("get");

		this.addElement(new FormElementInput("text", "name")
			.setLabel("Nombre")
			.setRequired(false)
			.addValidator("MaxLength", "50")
			.addValidator("IsAlphaNumS"));

		this.addElement(new FormElementInput("text", "description")
			.setLabel("Descripción")
			.setRequired(false)
			.addValidator("MaxLength", "50")
			.addValidator("IsAlphaNumS"));

		this.addElement(new FormElementInput("text", "maxPrice")
			.setLabel("Precio máximo")
			.setRequired(false)
			.addValidator("MaxLength", "5")
			.addValidator("IsDecimal"));

		this.addElement(new FormElementInput("text", "numberOfPlayers")
			.setLabel("Cantidad de jugadores")
			.setRequired(false)
			.addValidator("MaxLength", "5")
			.addValidator("IsNumeric"));

		this.addElement(new FormElementSelect("hasRoof")
			.setLabel("Techada")
			.setRequired(false)
			.addValue("", "")
			.addValue("Sí", "yes")
			.addValue("No", "no"));
	
		this.addElement(new FormElementSelect("floorType")
			.setLabel("Piso")
			.setRequired(false)
			.addValue("", "")
			.addValue(FloorType.CONCRETE.toString(), "CONCRETE")
			.addValue(FloorType.ARTIFICIAL_GRASS.toString(), "ARTIFICIAL_GRASS")
			.addValue(FloorType.GRASS.toString(), "GRASS"));

		Decorator decorator = new Decorator()
			.setSclass("submit-go"); 
	
		this.addElement(new FormElementButton("submit", "search")
			.setValue("Buscar")
			.setDecorator(decorator));


		sfield.add("name");
		sfield.add("description");
		sfield.add("maxPrice");
		sfield.add("numberOfPlayers");
		sfield.add("hasRoof");
		sfield.add("floorType");
		sfield.add("search");
		this.addDisplayGroup(sfield, "Búsqueda de canchas");
	}
}

//<label for="hasRoof">Techada: </label>
//Sí
//<input id="hasRoofYes" type="radio" name="hasRoof" value="yes"/>
//No
//<input id="hasRoofNo" type="radio" name="hasRoof" value="no"/>
//<br /><br />
//<label for="floorType">Tipo de piso: </label>
//<select name="floorType">
//	<option value=""> </option>
//	<option value="1">Césped sintético</option>
//	<option value="0">Césped</option>
//	<option value="2">Concreto</option>
//</select>
