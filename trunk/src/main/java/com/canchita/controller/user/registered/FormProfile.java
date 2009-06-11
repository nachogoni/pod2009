package com.canchita.controller.user.registered;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.canchita.views.helpers.form.Decorator;
import com.canchita.views.helpers.form.FormElementButton;
import com.canchita.views.helpers.form.FormElementInput;
import com.canchita.views.helpers.form.FormHandler;

public class FormProfile extends FormHandler {

	/*
	 * TODO MAILSs
	 */

	private int mailAmount;
	private static final String ELEMENT_LABEL = "Correo Electrónico";
	private static final String ELEMENT_NAME = "email";

	public FormProfile(List<String> mails) {

		this(mails.size());

		int i = 1;
		for (String mail : mails) {
			this.setElementValue(ELEMENT_NAME + i++, mail);
		}

	}

	public FormProfile(int mailAmount) {
		super();

		this.mailAmount = mailAmount;

		ArrayList<String> profile = new ArrayList<String>();
		ArrayList<String> sconfirm = new ArrayList<String>();

		this.setName("Perfil").setMethod("post");


		Decorator decoMails = new Decorator().setSclass("profileMails");

		for (int i = 1; i <= mailAmount; i++) {
			this.addElement(new FormElementInput("text", ELEMENT_NAME + i)
					.setLabel(ELEMENT_LABEL + i).setRequired(true)
					.addValidator("IsEMailAddress").setDecorator(decoMails));

		}

		Decorator decoBotones = new Decorator().setSclass("submit-go");

		this.addElement(new FormElementButton("submit", "submit").setValue(
				"Confirmar").setDecorator(decoBotones));

		this.addElement(new FormElementButton("reset", "reset").setValue(
				"Reset").setDecorator(decoBotones));

		/* agrego los grupos */

		for (int i = 1; i <= mailAmount; i++) {
			profile.add(ELEMENT_NAME + i);
		}

		Decorator formDecorator = new Decorator().setSclass("profileMailForm");

		this.addDisplayGroup(profile, "Perfil").setFormDecorator(formDecorator);

		sconfirm.add("submit");
		sconfirm.add("reset");
		this.addDisplayGroup(sconfirm, "Confirmación");

	}

	public int getMailAmount() {

		return this.mailAmount;
	}

	public Map<String, String> getUpdatedMails(FormProfile form) {

		Map<String, String> updated = new HashMap<String, String>();

		for (int i = 0; i < this.mailAmount; i++) {

			String oldValue = this.getElement(ELEMENT_NAME + (i + 1))
					.getValue();
			String newValue = form.getElement(ELEMENT_NAME + (i + 1))
					.getValue();

			if (!oldValue.equals(newValue)) {
				updated.put(oldValue, newValue);
			}
		}

		return updated;
	}

}
