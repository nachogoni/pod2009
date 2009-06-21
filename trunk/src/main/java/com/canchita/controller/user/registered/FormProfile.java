package com.canchita.controller.user.registered;

import java.util.ArrayList;
import java.util.List;

import com.canchita.model.exception.UserException;
import com.canchita.model.user.Registered;
import com.canchita.views.helpers.form.Decorator;
import com.canchita.views.helpers.form.FormElementButton;
import com.canchita.views.helpers.form.FormElementInput;
import com.canchita.views.helpers.form.FormElementSelect;
import com.canchita.views.helpers.form.FormHandler;
import com.canchita.views.helpers.form.Pair;

public class FormProfile extends FormHandler {

	public FormProfile() {
		super();
		ArrayList<String> sEmails = new ArrayList<String>();
		ArrayList<String> sNotifyBeforeExpiration = new ArrayList<String>();
		ArrayList<String> sConfirm = new ArrayList<String>();

		this.setName("Perfil").enableJ2Query(
				"src/main/webapp/js/user/profile/init.js").setMethod("post");

		this.addElement(new FormElementInput("text", "email")
				.setLabel("Correo")
				.setId("idMail")
				.setRequired(true)
				.addValidator("IsEMailAddress")
				.addValidator("MaxLength", "50")
				.setMaxLength(50)
				.setMultipleData("multipleEmail")
				.addJ2QueryTooltip("Correo electr&oacute;nico del usuario"));

		ArrayList<String> days = new ArrayList<String>();
		for (Integer i = 0; i < 32; i++) {
			days.add("" + i);
		}
		this.addElement(new FormElementSelect("notifyBeforeExpiration")
				.setLabel("Días").addValues(days, days).addJ2QueryTooltip(
						"Días antes de que el usuario sea "
								+ "notificado de una cauida de reserva."));

		Decorator decoBotones = new Decorator().setSclass("submit-go");

		this.addElement(new FormElementButton("submit", "submit").setValue(
				"Confirmar").setDecorator(decoBotones));

		this.addElement(new FormElementButton("reset", "reset").setValue(
				"Reset").setDecorator(decoBotones));

		sEmails.add("email");
		this.addDisplayGroup(sEmails, "Correo Electrónico");

		sNotifyBeforeExpiration.add("notifyBeforeExpiration");
		this.addDisplayGroup(sNotifyBeforeExpiration, "Notificación");

		sConfirm.add("submit");
		sConfirm.add("reset");
		this.addDisplayGroup(sConfirm, "Confirmaci&oacute;n");
	}

	public FormProfile(Registered user) {
		this();

		ArrayList<Pair<String, String>> data = new ArrayList<Pair<String, String>>();

		List<String> emails = null;
		try {
			emails = user.getEmails();

			if (!emails.isEmpty()) {
				for (String email : emails) {
					data.add(new Pair<String, String>("email", email));
				}
			}

		} catch (UserException e) {
			// Si atrapo excepción es porque está muerta la db.
			// La dejo pasar porque lo único que hace es dejar
			// los campos de emails sin llenar.
		}

		String notifyBeforeExpiration = user.getNotifyBeforeExpiration()
				.toString();
		data.add(new Pair<String, String>("notifyBeforeExpiration",
				notifyBeforeExpiration));
		
		this.populate(data);
	}
}
