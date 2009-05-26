package com.canchita.controller.complex;

import java.util.ArrayList;
import java.util.HashMap;

import com.canchita.model.complex.Complex;
import com.canchita.views.helpers.Decorator;
import com.canchita.views.helpers.FormElementButton;
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
		
		Decorator decoBotones = new Decorator()
								.setSclass("submit-go"); 
		
		this.addElement(new FormElementButton("submit","submit")
			.setValue("Confirmar")
			.setDecorator(decoBotones));
		
		this.addElement(new FormElementButton("reset","reset")
			.setValue("Reset")
			.setDecorator(decoBotones));
		
		
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

		String aux;
		Integer iaux;
		HashMap<String, String> dataPopu = new HashMap<String, String>();
		
		
		aux = aComplex.getName();
		if (aux != null)
			dataPopu.put("name", aux);
		
		aux = aComplex.getDescription();
		if (aux != null)
			dataPopu.put("description", aux);
		
		aux = aComplex.getPlace().getAddress();
		if (aux != null)
			dataPopu.put("address", aux);
		
		aux = aComplex.getPlace().getNeighbourhood();
		if (aux != null)
			dataPopu.put("neighbourhood", aux);
		
		aux = aComplex.getPlace().getTown();
		if (aux != null)
			dataPopu.put("town", aux);
		
		aux = aComplex.getPlace().getState();
		if (aux != null)
			dataPopu.put("state", aux);
		
		aux = aComplex.getPlace().getZipCode();
		if (aux != null)
			dataPopu.put("zipcode", aux);
		
		aux = aComplex.getPlace().getLatitude();
		if (aux != null)
			dataPopu.put("latitude", aux);
		
		aux = aComplex.getPlace().getLongitude();
		if (aux != null)
			dataPopu.put("longitude", aux);
		
		aux = aComplex.getPlace().getTelephones().get(0);
		if (aux != null)
			dataPopu.put("telephone", aux);
		
		aux = aComplex.getPlace().getCountry();
		if (aux != null)
			dataPopu.put("country", aux);
		
		iaux = aComplex.getScoreSystem().getBooking();
		if (iaux != null)
			dataPopu.put("booking", iaux.toString());
		
		iaux = aComplex.getScoreSystem().getDeposit();
		if (iaux != null)
			dataPopu.put("deposit", iaux.toString());
		
		iaux = aComplex.getScoreSystem().getPay();
		if (iaux != null)
			dataPopu.put("pay", iaux.toString());
		
		iaux = aComplex.getScoreSystem().getDownBooking();
		if (iaux != null)
			dataPopu.put("downBooking", iaux.toString());
		
		iaux = aComplex.getScoreSystem().getDownDeposit();
		if (iaux != null)
			dataPopu.put("downDeposit", iaux.toString());
		
		iaux = aComplex.getExpiration().getDepositLimit();
		if (iaux != null)
			dataPopu.put("depositLimit", iaux.toString());
		
		
		iaux = aComplex.getExpiration().getBookingLimit();
		if (iaux != null)
			dataPopu.put("bookingLimit", iaux.toString());
		
		
		this.populate(dataPopu);
	}
}
