package com.canchita.views.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.canchita.helper.validator.*;
import com.canchita.model.db.DataBaseConnection;

public abstract class FormHandler {
	protected ArrayList<FormElement> formElements;
	protected ArrayList<String> groupsOrder;
	protected HashMap<String,ArrayList<FormElement>> groups;
	protected HashMap<String, FormElement> formValues;
	protected HashMap<String, String> errors;
	protected ArrayList<String> attributes;
	protected String method;
	private Decorator formDecorator;
	private String name;
	Logger logger = Logger.getLogger(DataBaseConnection.class.getName());
	
	public FormHandler() {
		errors = new HashMap<String, String>();
		formValues = new HashMap<String, FormElement>();
		formElements = new ArrayList<FormElement>();
		attributes = new ArrayList<String>();
		groups = new HashMap<String, ArrayList<FormElement>>();
		groupsOrder = new ArrayList<String>();
		formDecorator = new Decorator();
		name = "form";
		method = "post";
	}
	

	public FormHandler addDisplayGroup(ArrayList<String> elements,
			String group) {
		ArrayList<FormElement> temp;

		this.groupsOrder.add(group);
		
		if ( (temp = this.groups.get(group)) == null) {
			this.groups.put(group, new ArrayList<FormElement>());
		}
		
		temp = this.groups.get(group);
		
		for (String aName:elements) {
			FormElement e = this.formValues.get(aName);
			temp.add(e);
		}

		return this;
	}
	
	private boolean hasGroups() {
		return !this.groups.isEmpty();
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
	
	/**
	 * Setea el value de un elemento del form
	 * 
	 * @param name nombre del elemento
	 * @param value valor a asignar
	 */
	public void setElementValue(String name, String value) {
		// TODO Auto-generated method stub
		FormElement e = formValues.get(name);
		if (e != null)
			e.setValue(value);	
	}
	
	public String toString() {

		String ret;
		String err;

		if (this.hasGroups()) {
			return this.printGroups();
		}
		ret = "";
		
		ret += String.format("<form name=\"%s\" action=\"\" method=\"%s\" %s>",
				this.name, this.method, this.getAttributesString());
		
		for (FormElement e : formElements) {
			ret += e;
			if ((err = this.errors.get(e.getName())) != null)
				ret += "<div class='errors'>" + err + "</div>";
		}
			
		ret += "</form>";
		
		return ret;
	}

	private String printGroups() {
		String err;
		String ret = "";

		ret += String.format("<form name=\"%s\" action=\"\" method=\"%s\" %s>",
				this.name, this.method, this.getAttributesString());

		for (String aName : this.groupsOrder) {
			
			ArrayList<FormElement> elements;
			elements = this.groups.get(aName);
			
			ret += 	String.format("<fieldset><legend>%s</legend>", aName);
			
			for (FormElement e : elements) {
				ret += e;
				if ((err = this.errors.get(e.getName())) != null)
					ret += "<div class='errors'>" + err + "</div>";
			}
			ret += "</fieldset>";
		}

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
			/* si es un input solamente tiene que recargar los values */
			if (e instanceof FormElementInput || e instanceof FormElementSelect)
				e.setValue(request.getParameter(e.getName()));
		}
			
	}

	/*
	public void loadValues(HashMap<String, String> dict) {
		for (FormElement e : this.formElements) {
			e.setValue(dict.get(e.getName()));
		}			
	}
	*/
	
	/*
	 * Llena el formulario con los parametros pasados
	 * 
	 * String: name del elemento dentro del formulario
	 * String: value a setear
	 */
	public void populate(HashMap<String, String> data){
		for (FormElement e : formElements){
			if (data.get(e.getName()) != null){
				e.setValue(data.get(e.getName()));
			}
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
						//Levanto la clase dinamicamente si no puede tira error en consola
						try{
							Class cvalidator = Class.forName("com.canchita.helper.validator." + val);
							try{
								aVal = (Validator)cvalidator.newInstance();
							}catch (IllegalAccessException exp) {
								logger.error("IllegalAccessException: al crear el validador " + val);
							}catch (InstantiationException e2) {
								logger.error("InstantiationException: al crear el validador " + val);
							}
						} catch (ClassNotFoundException except) {
							logger.error("ClassNotFoundException: al crear el validador " + val + " se crea el default");
							aVal = new IsEmpty();
						}

						//Si tiene parametros lo seteo
						if (aVal instanceof ValidatorWParam){
							((ValidatorWParam) aVal).setParam(e.validatorValues.get(val));
						}
						
						if ( !aVal.validate(e.getValue()) ){
							this.errors.put(e.getName(), aVal.getError());
							ret = false;
						}	
					}
				}
			}
		}
		
		return ret;
	}

	public void setAttribute(String a, String b) {
		this.attributes.add(a+ "=\"" +b + "\" ");
		
	}
	
	public void setMethod(String aMethod) {
		this.method = aMethod;
	}

	public Decorator getFormDecorator() {
		return formDecorator;
	}

	public FormHandler setFormDecorator(Decorator formDecorator) {
		this.formDecorator = formDecorator;
		return this;
	}

	public String getName() {
		return name;
	}

	public FormHandler setName(String name) {
		this.name = name;
		return this;
	}

}
