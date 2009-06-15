package com.canchita.views.helpers.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.canchita.helper.validator.IsEmpty;
import com.canchita.helper.validator.Validator;
import com.canchita.helper.validator.ValidatorWParam;
import com.canchita.model.db.DataBaseConnection;
import com.canchita.views.helpers.j2query.J2Query;

public abstract class FormHandler {
	protected ArrayList<FormElement> formElements;
	protected ArrayList<String> groupsOrder;
	protected HashMap<String, ArrayList<FormElement>> groups;
	protected HashMap<String, FormElement> formValues;
	protected HashMap<String, String> errors;
	protected ArrayList<String> attributes;
	protected String method;
	private Decorator formDecorator;
	private String name;
	private J2Query jjqueryInstance = null;
	private boolean j2queryenabled;

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
		j2queryenabled = false;
	}

	public boolean isJ2QueryEnabled() {
		return j2queryenabled;
	}

	public FormHandler addDisplayGroup(ArrayList<String> elements, String group) {
		ArrayList<FormElement> temp;

		this.groupsOrder.add(group);

		if ((temp = this.groups.get(group)) == null) {
			this.groups.put(group, new ArrayList<FormElement>());
		}

		temp = this.groups.get(group);

		for (String aName : elements) {
			FormElement e = this.formValues.get(aName);
			temp.add(e);
		}

		return this;
	}

	/**
	 * Habilita el uso del J2Query
	 * 
	 * @param path
	 * @return
	 */
	public FormHandler enableJ2Query(String path) {
		jjqueryInstance = new J2Query(path);
		j2queryenabled = true;

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
	 * @param name
	 *            nombre del elemento
	 * @param value
	 *            valor a asignar
	 */
	public void setElementValue(String name, String value) {
		FormElement e = formValues.get(name);
		if (e != null)
			e.setValue(value);
	}

	/**
	 * Imprime el formulario
	 */
	public String toString() {

		String ret;
		String err, sclass="";

		if (this.hasGroups()) {
			return this.printGroups();
		}
		ret = "";

		//Clase del decorador
		sclass = (formDecorator.getSclass().isEmpty()) ? "" : "class=\"" + formDecorator.getSclass() + "\"";
		
		ret += String.format("<form name=\"%s\" %s action=\"\" method=\"%s\" %s>",
				this.name, sclass, this.method, this.getAttributesString());

		for (FormElement e : formElements) {
			ret += e;
			// Agrego los JJQuerys si esta habilitado
			if (isJ2QueryEnabled())
				jjqueryInstance.addElements(e.getJJQueryElements());
			if ((err = this.errors.get(e.getName())) != null)
				ret += "<div class='errors'>" + err + "</div>";
		}

		ret += "</form>";

		// Si esta habilitado JJQuery genero el archivo
		if (isJ2QueryEnabled()) {
			jjqueryInstance.generate();
		}

		return ret;
	}

	/**
	 * Imprime el formulario segun grupos
	 * 
	 * @return
	 */
	private String printGroups() {
		String err;
		String ret = "";

		ret += String.format("<form name=\"%s\" action=\"\" method=\"%s\" %s>",
				this.name, this.method, this.getAttributesString());

		for (String aName : this.groupsOrder) {

			ArrayList<FormElement> elements;
			elements = this.groups.get(aName);

			ret += String.format("<fieldset><legend>%s</legend>", aName);

			for (FormElement e : elements) {
				ret += e;
				// Agrego los JJQuerys
				if (isJ2QueryEnabled())
					jjqueryInstance.addElements(e.getJJQueryElements());

				if ((err = this.errors.get(e.getName())) != null)
					ret += "<div class='errors'>" + err + "</div>";
			}
			ret += "</fieldset>";
		}

		ret += "</form>";

		// Si esta habilitado JJQuery genero el archivo
		if (isJ2QueryEnabled()) {
			jjqueryInstance.generate();
		}
		return ret;
	}

	private String getAttributesString() {
		String ret = "";

		for (String attr : attributes) {
			ret += attr;
		}

		return ret;
	}

	public void loadValues(HttpServletRequest request) {
		for (FormElement e : this.formElements) {
			/* si es un input solamente tiene que recargar los values */
			if (e instanceof FormElementSelect) {
				e.setValue(request.getParameter(e.getName()));
			} else if (e instanceof FormElementInput) {
				// Seteo el valor
				e.setValue(request.getParameter(e.getName()));
				// Si tiene mas elementos
				if (((FormElementInput) e).isMultipleData()) {
					// Busco los subelementos y los agrego
					String data[] = request.getParameterValues(e.getName());
					/* hago el load de parametros */
					int i = 1;
					for (; i < ((FormElementInput) e).subelements.size()
							&& i < data.length; i++) {
						((FormElementInput) e).subelements.get(i - 1).setValue(
								data[i]);
					}

					// Si me faltan valores los agrego
					for (; i < data.length; i++) {
						if (!data[i].isEmpty()) {
							// Si lo agrego el unico requerido es el inicial
							((FormElementInput)e).addSubElement(data[i]);
						}
					}
				}
			}
		}
	}

	/**
	 * Llena el formulario con un con la lista de claves (Nombre, Value) 
	 * 
	 * @param data
	 */
	public void populate(HashMap<String, String> data) {
		for (FormElement e : formElements) {
			if (data.get(e.getName()) != null) {
				e.setValue(data.get(e.getName()));
			}
		}
	}
	
	/**
	 * Llena el formulario con una lista de elementos Pair(key, value)
	 * Acepta repetidos para el caso que haya multiples valores 
	 * 
	 * @param data
	 */
	public void populate(List<Pair<String, String>> data) {
		//Seteo todos los elementos en blanco
		for (FormElement e : formElements) {
			if (!(e instanceof FormElementButton))
				e.setValue("");
		}
		
		//Recorro la lista de elementos
		FormElement e = null;
		for(Pair<String,String> d:data){
			//Obtengo el objeto
			if ((e = formValues.get(d.getFirst())) != null){
				if (e instanceof FormElementInput){
					//Si el valor esta seteado tengo que agregar un sub-elemento
					if (e.getValue().isEmpty())
						e.setValue(d.getSecond());
					else{
						//Agrego los valores 
						((FormElementInput)e).addSubElement(d.getSecond());
					}
				}else{
					e.setValue(d.getSecond());
				}
			}
		}
	}

	/**
	 * Valida que un formulario sea correcto
	 * 
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public boolean isValid() {
		boolean ret = true;
		Validator aVal;

		// por cada elemento del formulario
		for (FormElement e : formElements) {
			// Si es requerido y tiene validadores
			if (e.isRequired()) {

				// Valido que no este vacio
				aVal = new IsEmpty();
				if (!aVal.validate(e.getValue())) {
					this.errors.put(e.getName(), aVal.getError());
					ret = false;
				}

				if (e.validators != null) {
					// por cada validador
					for (String val : e.validators) {
						// Levanto la clase dinamicamente si no puede tira error
						// en consola
						try {
							Class cvalidator = Class
									.forName("com.canchita.helper.validator."
											+ val);
							try {
								aVal = (Validator) cvalidator.newInstance();
							} catch (IllegalAccessException exp) {
								logger
										.error("IllegalAccessException: al crear el validador "
												+ val);
							} catch (InstantiationException e2) {
								logger
										.error("InstantiationException: al crear el validador "
												+ val);
							}
						} catch (ClassNotFoundException except) {
							logger
									.error("ClassNotFoundException: al crear el validador "
											+ val + " se crea el default");
							aVal = new IsEmpty();
						}

						// Si tiene parametros lo seteo
						if (aVal instanceof ValidatorWParam) {
							((ValidatorWParam) aVal).setParam(e.validatorValues
									.get(val));
						}

						//Si es un formElementInput veo si tiene subelementos y los valido
						if (e instanceof FormElementInput){
							if (((FormElementInput) e).isMultipleData()){
								for (FormElement j:((FormElementInput) e).subelements){
									if (!aVal.validate(j.getValue())) {
										this.errors.put(j.getName(), aVal.getError());
										ret = false;
									}
								}
							}
						}
						
						//Valido el elemento
						if (!aVal.validate(e.getValue())) {
							this.errors.put(e.getName(), aVal.getError());
							ret = false;
						}
					}
				}
			}
		}
		return ret;
	}

	public void setAttribute(String type, String attribute) {
		this.attributes.add(type + "=\"" + attribute + "\" ");

	}

	public FormHandler setMethod(String aMethod) {
		this.method = aMethod;

		return this;
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
