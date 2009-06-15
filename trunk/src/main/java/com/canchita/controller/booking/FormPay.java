package com.canchita.controller.booking;

import java.util.ArrayList;
import java.util.HashMap;

import com.canchita.views.helpers.form.Decorator;
import com.canchita.views.helpers.form.FormElementButton;
import com.canchita.views.helpers.form.FormElementInput;
import com.canchita.views.helpers.form.FormHandler;

public class FormPay extends FormHandler {

	public FormPay() {
		ArrayList<String> pay = new ArrayList<String>();
		ArrayList<String> sconfirm = new ArrayList<String>();
		
		this.setName("Cargar Pago")
  		.setMethod("post");

		this.addElement(new FormElementInput("text","amount")
			.setLabel("Monto")
			.setRequired(true)
			.addValidator("IsDecimal"));
		
				
		this.addElement(new FormElementInput("hidden","id"));

		Decorator decorator = new Decorator()
			.setSclass("submit-go"); 
		
		this.addElement(new FormElementButton("submit","submit")
			.setValue("Confirmar")
			.setDecorator(decorator));
	
		this.addElement(new FormElementButton("reset","reset")
			.setValue("Reset")
			.setDecorator(decorator));
        
        pay.add("amount");
        pay.add("id");
        this.addDisplayGroup(pay, "Cargar Pago");
        
        sconfirm.add("submit");
		sconfirm.add("reset");
		this.addDisplayGroup(sconfirm, "Confirmación");
                
	}
	
	public FormPay(Long id) {
		this();

		HashMap<String, String> dataPopu = new HashMap<String, String>();
		
		dataPopu.put("id",id.toString());
		
		this.populate(dataPopu);
	}

}
