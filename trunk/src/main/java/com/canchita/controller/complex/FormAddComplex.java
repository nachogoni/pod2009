package com.canchita.controller.complex;

import java.util.ArrayList;
import java.util.HashMap;

import com.canchita.model.complex.Complex;
import com.canchita.views.helpers.form.Decorator;
import com.canchita.views.helpers.form.FormElementButton;
import com.canchita.views.helpers.form.FormElementInput;
import com.canchita.views.helpers.form.FormElementSelect;
import com.canchita.views.helpers.form.FormHandler;

public class FormAddComplex extends FormHandler {

	public FormAddComplex() {
		super();
		ArrayList<String> scomplejo = new ArrayList<String>();
		ArrayList<String> subicacion = new ArrayList<String>();
		
		ArrayList<String> slunes = new ArrayList<String>();
		ArrayList<String> smartes = new ArrayList<String>();
		ArrayList<String> smiercoles = new ArrayList<String>();
		ArrayList<String> sjueves = new ArrayList<String>();
		ArrayList<String> sviernes = new ArrayList<String>();
		ArrayList<String> ssabado = new ArrayList<String>();
		ArrayList<String> sdomingo = new ArrayList<String>();
		
		
		ArrayList<String> sexpiration = new ArrayList<String>();
		ArrayList<String> sconfirm = new ArrayList<String>();
		
		ArrayList<String> horas = new ArrayList<String>();
		
		for(int i=0;i<24;i++){
			horas.add(String.format("%02d:%02d", i, ((i%2)==0)?0:30));
		}
		
		this.setName("Complejo")
			.enableJ2Query("js/complex/add/init.js")
	  		.setMethod("post");
			     
		this.addElement(new FormElementInput("text","name")
			.setLabel("Nombre")
			.setId("idNombre")
			.setRequired(true)
			.addValidator("MaxLength", "20")
			.addValidator("IsAlphaNumS")
			.addJ2QueryTooltip("Nombre del Complejo"));

		this.addElement(new FormElementInput("text","description")
			.setLabel("Descripción")
			.setId("idDescripcion")
			.setRequired(false))
			.addJ2QueryTooltip("Descripcion del Complejo");
		
		this.addElement(new FormElementInput("text","address")
			.setLabel("Dirección")
			.setId("idDireccion")
			.setRequired(true)
			.addValidator("IsAddress")
			.addJ2QueryTooltip("Direccion del Complejo"));

		this.addElement(new FormElementInput("text","neighbourhood")
			.setLabel("Barrio")
			.setId("idNeighbourhood")
			.setRequired(true)
			.addValidator("IsAlphaNumS")
			.addJ2QueryTooltip("Barrio del Complejo"));

		this.addElement(new FormElementInput("text","town")
			.setLabel("Ciudad")
			.setRequired(true)
			.addValidator("IsAlphaNumS")
			.setId("idTown")
			.addJ2QueryTooltip("Ciudad del Complejo"));
		
		this.addElement(new FormElementInput("text","state")
			.setLabel("Provincia")
			.setRequired(true)
			.addValidator("IsAlphaNumS")
			.setId("idState")
			.addJ2QueryTooltip("Provincia del Complejo"));

		this.addElement(new FormElementInput("text","zipcode")
			.setLabel("Código Postal")
			.setRequired(true)
			.addValidator("IsNumeric")
			.setId("idZipCode")
			.addJ2QueryTooltip("Codigo Postal del Complejo"));

		this.addElement(new FormElementInput("text","latitude")
			.setLabel("Latitud")
			.setId("idLatitude")
			.addJ2QueryTooltip("Latidud del Complejo"));

		this.addElement(new FormElementInput("text","longitude")
			.setLabel("Longitud")
			.setId("idLongitude")
			.addJ2QueryTooltip("Longitud del Complejo"));

		this.addElement(new FormElementInput("text","telephone")
			.setLabel("Teléfono")
			.setId("idTelephone")
			.setRequired(true)
			.addValidator("IsTelephone")
			.setMultipleData("multiplePhone")
			.addJ2QueryTooltip("Telefono del Complejo"));

		this.addElement(new FormElementInput("text","country")
			.setRequired(true)
			.addValidator("IsAlphaNumS")
			.setId("idCountry")
			.setLabel("País")
			.addJ2QueryTooltip("Pais del Complejo"));
		
		/* Horarios lunes */
		this.addElement(new FormElementSelect("fecha_lunes_inicio")
			.setLabel("Inicio")
			.addValues(horas, horas));
		
		this.addElement(new FormElementSelect("fecha_lunes_fin")
			.setLabel("Fin")
			.addValues(horas, horas));
		/* fin lunes */
		
		/* Horarios martes */
		this.addElement(new FormElementSelect("fecha_martes_inicio")
			.setLabel("Inicio")
			.addValues(horas, horas));
		
		this.addElement(new FormElementSelect("fecha_martes_fin")
			.setLabel("Fin")
			.addValues(horas, horas));
		/* fin martes */
		
		/* Horarios miercoles */
		this.addElement(new FormElementSelect("fecha_miercoles_inicio")
			.setLabel("Inicio")
			.addValues(horas, horas));
		
		this.addElement(new FormElementSelect("fecha_miercoles_fin")
			.setLabel("Fin")
			.addValues(horas, horas));
		/* fin miercoles */
		
		/* Horarios jueves */
		this.addElement(new FormElementSelect("fecha_jueves_inicio")
			.setLabel("Inicio")
			.addValues(horas, horas));
		
		this.addElement(new FormElementSelect("fecha_jueves_fin")
			.setLabel("Fin")
			.addValues(horas, horas));
		/* fin jueves */
		
		/* Horarios viernes */
		this.addElement(new FormElementSelect("fecha_viernes_inicio")
			.setLabel("Inicio")
			.addValues(horas, horas));
		
		this.addElement(new FormElementSelect("fecha_viernes_fin")
			.setLabel("Fin")
			.addValues(horas, horas));
		/* fin viernes */
		
		/* Horarios sabado */
		this.addElement(new FormElementSelect("fecha_sabado_inicio")
			.setLabel("Inicio")
			.addValues(horas, horas));
		
		this.addElement(new FormElementSelect("fecha_sabado_fin")
			.setLabel("Fin")
			.addValues(horas, horas));
		/* fin sabado  */
		
		/* Horarios domingo */
		this.addElement(new FormElementSelect("fecha_domingo_inicio")
			.setLabel("Inicio")
			.addValues(horas, horas));
		
		this.addElement(new FormElementSelect("fecha_domingo_fin")
			.setLabel("Fin")
			.addValues(horas, horas));
		/* fin domingo */

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
		this.addDisplayGroup(subicacion, "Ubicación");
		
		slunes.add("fecha_lunes_inicio");
		slunes.add("fecha_lunes_fin");
		this.addDisplayGroup(slunes, "Lunes");
		
		smartes.add("fecha_martes_inicio");
		smartes.add("fecha_martes_fin");
		this.addDisplayGroup(smartes, "Martes");
		
		smiercoles.add("fecha_miercoles_inicio");
		smiercoles.add("fecha_miercoles_fin");
		this.addDisplayGroup(smiercoles, "Miercoles");
		
		sjueves.add("fecha_jueves_inicio");
		sjueves.add("fecha_jueves_fin");
		this.addDisplayGroup(sjueves, "Jueves");
		
		sviernes.add("fecha_viernes_inicio");
		sviernes.add("fecha_viernes_fin");
		this.addDisplayGroup(sviernes, "viernes");
		
		ssabado.add("fecha_sabado_inicio");
		ssabado.add("fecha_sabado_fin");
		this.addDisplayGroup(ssabado, "Sabado");
		
		sdomingo.add("fecha_domingo_inicio");
		sdomingo.add("fecha_domingo_fin");
		this.addDisplayGroup(sdomingo, "Domingo");
		
		sexpiration.add("depositLimit");
		sexpiration.add("bookingLimit");
		this.addDisplayGroup(sexpiration, "Seña y Pago de Reservas");

		sconfirm.add("submit");
		sconfirm.add("reset");
		this.addDisplayGroup(sconfirm, "Confirmación");
		
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
		
		if (!aComplex.getPhones().isEmpty()) {
			aux = aComplex.getPhones().get(0);
			dataPopu.put("telephone", aux);
		}
			
		aux = aComplex.getPlace().getCountry();
		if (aux != null)
			dataPopu.put("country", aux);
		
		if (aComplex.getExpiration() != null) {
			
			iaux = aComplex.getExpiration().getDepositLimit();
			if (iaux != null)
				dataPopu.put("depositLimit", iaux.toString());
			
			iaux = aComplex.getExpiration().getBookingLimit();
			if (iaux != null)
				dataPopu.put("bookingLimit", iaux.toString());
		}		
		
		this.populate(dataPopu);
	}
}
