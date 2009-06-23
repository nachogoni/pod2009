package com.canchita.controller.admin;

import java.util.ArrayList;
import java.util.List;

import com.canchita.model.exception.UserException;
import com.canchita.model.user.Registered;
import com.canchita.views.helpers.form.Decorator;
import com.canchita.views.helpers.form.FormElementButton;
import com.canchita.views.helpers.form.FormElementInput;
import com.canchita.views.helpers.form.FormHandler;
import com.canchita.views.helpers.form.Pair;

public class FormEmail extends FormHandler {

	public FormEmail() {
		super();
		ArrayList<String> sEmails = new ArrayList<String>();
		ArrayList<String> sConfirm = new ArrayList<String>();

		this.setName("E-mails")
			//.enableJ2Query("src/main/webapp/js/admin/email/init.js")
			.setMethod("post");

		this.addElement(new FormElementInput("text", "email")
				.setLabel("Correo").setId("idMail").setRequired(true)
				.addValidator("IsEMailAddress")
				.setMaxLength(50)
				.setMultipleData("multipleEmail").addJ2QueryTooltip(
						"Correo electr&oacute;nico del usuario"));


		Decorator decoBotones = new Decorator().setSclass("submit-go");

		this.addElement(new FormElementButton("submit", "submit").setValue(
				"Confirmar").setDecorator(decoBotones));

		this.addElement(new FormElementButton("reset", "reset").setValue(
				"Reset").setDecorator(decoBotones));

		sEmails.add("email");
		this.addDisplayGroup(sEmails, "Correo Electrónico");

		sConfirm.add("submit");
		sConfirm.add("reset");
		this.addDisplayGroup(sConfirm, "Confirmaci&oacute;n");
	}

	public FormEmail(Registered user) {
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

		this.populate(data);
	}
}
