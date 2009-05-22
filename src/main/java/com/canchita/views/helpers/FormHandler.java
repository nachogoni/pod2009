package com.canchita.views.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

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
				ret += err;
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

	public abstract boolean isValid();

	public void setAttribute(String a, String b) {
		this.attributes.add(a+ "=\"" +b + "\" ");
		
	}
	
	public void setMethod(String aMethod) {
		this.method = aMethod;
	}
}
