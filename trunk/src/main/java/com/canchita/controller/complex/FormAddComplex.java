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
		ArrayList<String> scomplejo = new ArrayList<String>();
		ArrayList<String> subicacion = new ArrayList<String>();
		ArrayList<String> spuntos = new ArrayList<String>();

		this.setName("Complejo")
	  		.setMethod("post");
			      
		this.addElement(new FormElementInput("text","name")
			.setLabel("Nombre")
			.setRequired(true)
			.addValidator("IsAlphaNum"));

		this.addElement(new FormElementInput("text","description")
			.setLabel("Descripción")
			.setRequired(false));
		
		this.addElement(new FormElementInput("text","address")
			.setLabel("Direccion")
			.setRequired(true)
			.addValidator("IsAlphaNum"));

		this.addElement(new FormElementInput("text","neighbourhood")
			.setLabel("Barrio")
			.setRequired(true)
			.addValidator("IsAlphaNum"));

		this.addElement(new FormElementInput("text","town")
			.setLabel("Ciudad")
			.setRequired(true)
			.addValidator("IsAlphaNum"));
		
		this.addElement(new FormElementInput("text","state")
			.setLabel("Provincia")
			.setRequired(true)
			.addValidator("IsAlphaNum"));

		this.addElement(new FormElementInput("text","zipcode")
			.setLabel("Codigo Postal")
			.setRequired(true)
			.addValidator("IsAlphaNum"));

		this.addElement(new FormElementInput("text","latitude")
			.setLabel("Latitud"));

		this.addElement(new FormElementInput("text","longitude")
			.setLabel("Longitud"));

		this.addElement(new FormElementInput("text","telephone")
			.setLabel("Telefono"));

		this.addElement(new FormElementInput("text","country")
			.setLabel("Pais"));
		
		this.addElement(new FormElementInput("text","booking")
			.setLabel("Reservar")
			.setRequired(true)
			.addValidator("IsNumeric"));
		
		this.addElement(new FormElementInput("text","deposit")
			.setLabel("Señar")
			.setRequired(true)
			.addValidator("IsNumeric"));
		
		this.addElement(new FormElementInput("text","pay")
			.setLabel("Pagar")
			.setRequired(true)
			.addValidator("IsNumeric"));
		
		this.addElement(new FormElementInput("text","downBooking")
			.setLabel("Cancelar Reserva")
			.setRequired(true)
			.addValidator("IsNumeric"));
		
		this.addElement(new FormElementInput("text","downDeposit")
			.setLabel("Cancelar Deposito")
			.setRequired(true)
			.addValidator("IsNumeric"));

		/* agrego los grupos */
		scomplejo.add("name");
		scomplejo.add("description");
		this.addDisplayGroup(scomplejo, "Complejo");
		
		subicacion.add("address");
		subicacion.add("neighbourhood");
		subicacion.add("town");
		subicacion.add("state");
		subicacion.add("zipcode");
		subicacion.add("latitude");
		subicacion.add("longitude");
		subicacion.add("telephone");
		subicacion.add("country");
		this.addDisplayGroup(subicacion, "Ubicacion");
		
		spuntos.add("booking");
		spuntos.add("deposit");
		spuntos.add("pay");
		spuntos.add("downBooking");
		spuntos.add("downDeposit");
		this.addDisplayGroup(spuntos, "Sistema de Puntos");
		
	}
}
