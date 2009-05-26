package com.canchita.views.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.canchita.helper.validator.*;

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
			
		ret += "<input type=\"submit\" value=\"Agregar\">";
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
						//Levanto la clase dinamicamente si no puede tira error en consola
						try{
							Class cvalidator = Class.forName("com.canchita.helper.validator." + val);
							try{
								aVal = (Validator)cvalidator.newInstance();
							}catch (IllegalAccessException exp) {
								// TODO: handle exception
								System.out.println("Error 1");
							}catch (InstantiationException e2) {
								// TODO: handle exception
								System.out.println("Error 2");
							}
						} catch (ClassNotFoundException except) {
							// TODO: handle exception
							System.out.println("Error 3");
							aVal = new IsEmpty();
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
