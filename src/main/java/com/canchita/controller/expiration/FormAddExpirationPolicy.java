package com.canchita.controller.expiration;

import java.util.ArrayList;
import java.util.HashMap;

import com.canchita.model.booking.Expiration;
import com.canchita.views.helpers.form.Decorator;
import com.canchita.views.helpers.form.FormElementButton;
import com.canchita.views.helpers.form.FormElementInput;
import com.canchita.views.helpers.form.FormHandler;

public class FormAddExpirationPolicy extends FormHandler {

	public FormAddExpirationPolicy() {
		ArrayList<String> sfield = new ArrayList<String>();
		ArrayList<String> sconfirm = new ArrayList<String>();

		this.setName("Política de expiración").setMethod("post");

		this.addElement(new FormElementInput("text", "scoreFrom")
			.setLabel("Desde")
			.setRequired(true)
			.setMaxLength(20)
			.addValidator("IsNumericN"));

		this.addElement(new FormElementInput("text", "scoreTo")
			.setLabel("Hasta")
			.setRequired(true)
			.setMaxLength(20)
			.addValidator("IsNumericN"));

		this.addElement(new FormElementInput("text", "downBooking")
			.setLabel("Cae reservada")
			.setRequired(true)
			.setMaxLength(20)
			.addValidator("IsNumeric"));

		this.addElement(new FormElementInput("text", "downDeposit")
			.setLabel("Cae señada")
			.setRequired(true)
			.setMaxLength(20)
			.addValidator("IsNumeric"));
		
		Decorator decorator = new Decorator().setSclass("submit-go");

		this.addElement(new FormElementButton("submit", "submit").setValue(
				"Confirmar").setDecorator(decorator));

		this.addElement(new FormElementButton("reset", "reset").setValue(
				"Reset").setDecorator(decorator));

		sfield.add("scoreFrom");
		sfield.add("scoreTo");
		sfield.add("downBooking");
		sfield.add("downDeposit");
		this.addDisplayGroup(sfield, "Política de expiración");

		sconfirm.add("submit");
		sconfirm.add("reset");
		this.addDisplayGroup(sconfirm, "Confirmación");
	}

	public FormAddExpirationPolicy(Expiration expiration) {
		this();

		String aux;
		HashMap<String, String> dataPopu = new HashMap<String, String>();

		aux = expiration.getScoreFrom().toString();
		if (aux != null)
			dataPopu.put("scoreFrom", aux);

		aux = expiration.getScoreTo().toString();
		if (aux != null)
			dataPopu.put("scoreTo", aux);

		aux = expiration.getBookingLimit().toString();
		if (aux != null)
			dataPopu.put("downBooking", aux);

		aux = expiration.getDepositLimit().toString();
		if (aux != null)
			dataPopu.put("downDeposit", aux);

		this.populate(dataPopu);
	}

}