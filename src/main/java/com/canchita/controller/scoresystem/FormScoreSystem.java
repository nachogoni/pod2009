package com.canchita.controller.scoresystem;

import java.util.ArrayList;
import java.util.HashMap;

import com.canchita.model.complex.ScoreSystem;
import com.canchita.views.helpers.form.Decorator;
import com.canchita.views.helpers.form.FormElementButton;
import com.canchita.views.helpers.form.FormElementInput;
import com.canchita.views.helpers.form.FormHandler;

public class FormScoreSystem extends FormHandler {

	public FormScoreSystem() {
		ArrayList<String> sfield = new ArrayList<String>();
		ArrayList<String> sconfirm = new ArrayList<String>();

		this.setName("Sistema de Puntos").setMethod("post");

		this.addElement(new FormElementInput("text", "booking").setLabel(
				"Reserva").setRequired(true).addValidator("IsNumeric"));

		this.addElement(new FormElementInput("text", "deposit")
				.setLabel("Seña").setRequired(true).addValidator("IsNumeric"));

		this.addElement(new FormElementInput("text", "pay").setLabel("Pago")
				.setRequired(true).addValidator("IsNumeric"));

		this.addElement(new FormElementInput("text", "downBooking").setLabel(
				"Cae reservada").setRequired(true).addValidator(
				"IsNumeric"));

		this.addElement(new FormElementInput("text", "downDeposit").setLabel(
				"Cae señada").setRequired(true).addValidator(
				"IsNumeric"));

		Decorator decorator = new Decorator().setSclass("submit-go");

		this.addElement(new FormElementButton("submit", "submit").setValue(
				"Confirmar").setDecorator(decorator));

		this.addElement(new FormElementButton("reset", "reset").setValue(
				"Reset").setDecorator(decorator));

		sfield.add("booking");
		sfield.add("deposit");
		sfield.add("pay");
		sfield.add("downBooking");
		sfield.add("downDeposit");
		this.addDisplayGroup(sfield, "Sistema de Puntos");

		sconfirm.add("submit");
		sconfirm.add("reset");
		this.addDisplayGroup(sconfirm, "Confirmación");
	}

	public FormScoreSystem(ScoreSystem scoreSystem) {
		this();

		String aux;
		HashMap<String, String> dataPopu = new HashMap<String, String>();

		aux = scoreSystem.getBooking().toString();
		if (aux != null)
			dataPopu.put("booking", aux);

		aux = scoreSystem.getDeposit().toString();
		if (aux != null)
			dataPopu.put("deposit", aux);

		aux = scoreSystem.getPay().toString();
		if (aux != null)
			dataPopu.put("pay", aux);

		aux = scoreSystem.getDownBooking().toString();
		if (aux != null)
			dataPopu.put("downBooking", aux);

		aux = scoreSystem.getDownDeposit().toString();
		if (aux != null)
			dataPopu.put("downDeposit", aux);

		this.populate(dataPopu);
	}

}