package com.canchita.controller.user.guest;

import java.util.ArrayList;

import com.canchita.views.helpers.Decorator;
import com.canchita.views.helpers.FormElementButton;
import com.canchita.views.helpers.FormElementInput;
import com.canchita.views.helpers.FormHandler;

public class FormRegister extends FormHandler {

	/*
	 * TODO chequear realmente los valores de Min y MaxLength
	 */
	
	public FormRegister() {
		super();
		ArrayList<String> register = new ArrayList<String>();
		ArrayList<String> sconfirm = new ArrayList<String>();
		
		this.setName("Reserva")
	  		.setMethod("post");
			     
		this.addElement(new FormElementInput("text","name")
			.setLabel("Nombre de Usuario")
			.setRequired(true)
			.addValidator("IsAlphaNumS")
			.addValidator("MaxLength", "40")
			.addValidator("MinLength","2")
			);

		this.addElement(new FormElementInput("text","email")
			.setLabel("Correo Electrónico")
			.setRequired(true)
			.addValidator("IsEMailAddress")
			);

		
		this.addElement(new FormElementInput("password","pass")
			.setLabel("Contraseña")
			.setRequired(true)
			.addValidator("MaxLength", "40")
			.addValidator("MinLength","4")
			);
		
		this.addElement(new FormElementInput("password","passAgain")
			.setLabel("Repita Contraseña")
			.setRequired(true)
			.addValidator("MaxLength", "40")
			.addValidator("MinLength","4")
			);
		
		Decorator decoBotones = new Decorator()
			.setSclass("submit-go"); 
		
		this.addElement(new FormElementButton("submit","submit")
			.setValue("Confirmar")
			.setDecorator(decoBotones));
		
		this.addElement(new FormElementButton("reset","reset")
			.setValue("Reset")
			.setDecorator(decoBotones));
		
		
		/* agrego los grupos */
		register.add("name");
		register.add("email");
		register.add("pass");
		register.add("passAgain");
		this.addDisplayGroup(register, "Registración");
		
		sconfirm.add("submit");
		sconfirm.add("reset");
		this.addDisplayGroup(sconfirm, "Confirmación");
	}

}
