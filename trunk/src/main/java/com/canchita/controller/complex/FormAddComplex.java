package com.canchita.controller.complex;

import java.util.ArrayList;

import com.canchita.model.complex.Complex;
import com.canchita.views.helpers.FormElement;
import com.canchita.views.helpers.FormElementInput;
import com.canchita.views.helpers.FormHandler;

public class FormAddComplex extends FormHandler {

	public FormAddComplex() {
		super();
		ArrayList<String> scomplejo = new ArrayList<String>();
		ArrayList<String> subicacion = new ArrayList<String>();
		ArrayList<String> spuntos = new ArrayList<String>();
		ArrayList<String> sexpiration = new ArrayList<String>();
		ArrayList<String> sconfirm = new ArrayList<String>();

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
			.setLabel("Dirección")
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
			.setLabel("Código Postal")
			.setRequired(true)
			.addValidator("IsAlphaNum"));

		this.addElement(new FormElementInput("text","latitude")
			.setLabel("Latitud"));

		this.addElement(new FormElementInput("text","longitude")
			.setLabel("Longitud"));

		this.addElement(new FormElementInput("text","telephone")
			.setLabel("Teléfono"));

		this.addElement(new FormElementInput("text","country")
			.setLabel("País"));
		
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

		this.addElement(new FormElementInput("text","depositLimit")
			.setLabel("Límite de seña")
			.setId("depositLimit")
			.setRequired(true)
			.addValidator("IsNumeric"));
		
		this.addElement(new FormElementInput("text","bookingLimit")
			.setLabel("Límite de pago")
			.setId("bookingLimit")
			.setRequired(true)
			.addValidator("IsNumeric"));
		
		this.addElement(new FormElementInput("submit","submit")
			.setValue("Confirmar"));
		
		this.addElement(new FormElementInput("reset","reset")
			.setValue("Reset"));
		
		
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

		sexpiration.add("depositLimit");
		sexpiration.add("bookingLimit");
		this.addDisplayGroup(sexpiration, "Seña y Pago de Reservas");

		sconfirm.add("submit");
		sconfirm.add("reset");
		this.addDisplayGroup(sconfirm, "Confirmacion");
		
	}

	public FormAddComplex(Complex aComplex) {
		this();

		String val, aux;
		
		aux = aComplex.getName();
		val = (aux== null) ? "":aux;
		this.formValues.get("name")
			.setValue(val);

		aux = aComplex.getDescription();
		val = (aux== null) ? "":aux;
		this.formValues.get("description")
		   .setValue(val);

		aux = aComplex.getPlace().getAddress();
		val = (aux== null) ? "":aux;
		this.formValues.get("address")
            .setValue(val);

		aux = aComplex.getPlace().getNeighbourhood();
		val = (aux== null) ? "":aux;
		this.formValues.get("neighbourhood")
            .setValue(val);

		aux = aComplex.getPlace().getTown();
		val = (aux== null) ? "":aux;
		this.formValues.get("town")
	        .setValue(val);

		aux = aComplex.getPlace().getState();
		val = (aux== null) ? "":aux;
		this.formValues.get("state")
	        .setValue(val);

		aux = aComplex.getPlace().getZipCode();
		val = (aux== null) ? "":aux;
		this.formValues.get("zipcode")
	        .setValue(val);

		aux = aComplex.getPlace().getLatitude();
		val = (aux== null) ? "":aux;
		this.formValues.get("latitude")
	        .setValue(val);

		aux = aComplex.getPlace().getLongitude();
		val = (aux== null) ? "":aux;
		this.formValues.get("longitude")
	        .setValue(val);

		aux = aComplex.getPlace().getTelephones().get(0);
		val = (aux== null) ? "":aux;
		this.formValues.get("telephone")
	        .setValue(val);

		aux = aComplex.getPlace().getCountry();
		val = (aux== null) ? "":aux;
		this.formValues.get("country")
	        .setValue(val);

	}
}
