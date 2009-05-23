package com.canchita.views.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.canchita.views.helpers.validators.Validator;
import com.canchita.views.helpers.validators.*;

public abstract class FormHandler {
	protected ArrayList<FormElement> formElements;
	protected HashMap<String, FormElement> formValues;
	protected HashMap<String, String> errors;
	protected ArrayList<String> attributes;
	protected String method;

	public FormHandler() {
		errors = new HashMap<String, String>();
		formValues = new HashMap<String, FormElement>();
		formElements = new ArrayList<FormElement>();
		attributes = new ArrayList<String>();

	}
	public FormElement addElement(FormElement e) {
		this.formElements.add(e);
		this.formValues.put(e.getName(), e);
		return e;
	}

	public FormElement getElement(String aName) {
		return this.formValues.get(aName);
	}
	
	public void unsetErrors() {
		this.errors = new HashMap<String, String>();
	}
	
	public String toString() {

		String ret;
		String err;
		
		ret = String.format("<form action=\"\" method=\"%s\" %s>",
				this.method, this.getAttributesString());
		
		for (FormElement e : formElements) {
			ret += e;
			if ((err = this.errors.get(e.getName())) != null)
				ret += "<div class='errors'>" + err + "</div>";
		}
			
		ret += "<input type=\"submit\" value=\"Agregar\">";
		ret += "</form>";
		
		return ret;
	}

	private Object getAttributesString() {
		String ret = "";
		
		for (String attr : attributes) {
			ret += attr;
		}
		
		return ret;
	}

	public void loadValues(HttpServletRequest request) {
		for (FormElement e : this.formElements) {
			e.setValue(request.getParameter(e.getName()));
		}
			
	}

	/**
	 * Valida que un formulario sea correcto
	 * 
	 * @return boolean
	 */
	public boolean isValid(){
		boolean ret = true;
		Validator aVal;
		
		// por cada elemento del formulario
		for (FormElement e : formElements){
			//Si es requerido y tiene validadores
			if (e.isRequired()){
				
				//Valido que no este vacio
				aVal = new IsEmpty();
				if ( !aVal.validate(e.getValue()) ){
					this.errors.put(e.getName(), aVal.getError());
					ret = false;
				}
				
				if (e.validators != null)
				{
					//por cada validador
					for(String val: e.validators){
						//Si es un alphaNumerico
						if (val.equals("isAlphaNum"))
							aVal = new IsAlphaNum();
						else if (val.equals("isAlpha"))
							aVal = new IsAlpha();
						else
							aVal = new IsEmpty();
	
						if ( !aVal.validate(e.getValue()) ){
							this.errors.put(e.getName(), aVal.getError());
							ret = false;
						}
							
					}
				}
			}
		}
		    

		
		/*Name validations*/
		/*
		e = this.formValues.get("name");
		
		if (e.isRequired() && e.getValue().isEmpty()) {
			
		}
		*/
		
		/*Lastname validations*/
		/*
		e = this.formValues.get("lastname");
		if (!e.getValue().matches("[a-zA-ZñÑ]*")) {
			this.errors.put(e.getName(), "Este campo sólo permite letras.");
			ret = false;
		}
		*/
		
		return ret;
	}

	public void setAttribute(String a, String b) {
		this.attributes.add(a+ "=\"" +b + "\" ");
		
	}
	
	public void setMethod(String aMethod) {
		this.method = aMethod;
	}
}
