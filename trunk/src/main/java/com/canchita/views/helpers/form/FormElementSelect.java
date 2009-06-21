package com.canchita.views.helpers.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.canchita.model.db.DataBaseConnection;

public class FormElementSelect extends FormElement {

	protected ArrayList<FormElementSelectValue> options;
	protected boolean disable;
	Logger logger = Logger.getLogger(DataBaseConnection.class.getName());

	/**
	 * Constructor
	 * 
	 * @param name nombre del select
	 */
	public FormElementSelect(String name) {
		super("select", name);
		disable = false;
		options = new ArrayList<FormElementSelectValue>();
	}

	/**
	 * Agrega un valor al select
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public FormElementSelect addValue(String name, String value) {
		options.add(new FormElementSelectValue(name, value));

		return this;
	}

	/**
	 * Agrega un listado de pares nombre-valor al select
	 * 
	 * @param names
	 * @param values
	 * @return
	 */
	public FormElementSelect addValues(List<String> names, List<String> values) {
		int i=0;
		
		//Si los sizes son distintos salgo
		if ((names.size() != values.size()) && !names.isEmpty() && !values.isEmpty()){
			logger.debug("Los sizes del names y values son distintos");
			return null;
		}
		
		//no uso un iterador por que tengo que recorrer las dos listas al mismo tiempo
		for(i=0;i<names.size();i++){
			this.options.add(new FormElementSelectValue(names.get(i),values.get(i)));
		}

		return this;
	}

	/**
	 * Hace el html del select
	 */
	public String toString() {
		String ret = "", disabled="";

		if (this.isDisabled()){
			disabled = "disabled";
		}
		
		for (FormElementSelectValue e : options) {
			ret += e.toString();
		}

		return String.format("%s<select name=\"%s\" %s> %s </select>", 
				super.genLabel(), super.getName(), disabled , ret);
	}

	/**
	 * Setea default al elemento con name @value
	 * 
	 * @param value
	 */
	public FormElementSelect setValue(String value) {
		super.setValue(value);

		//Busco al elemento de name @value
		for (FormElementSelectValue e : options) {
			if (e.getValue().equals(value)) {
				e.setDefault(true);
			}
		}

		return this;
	}

	/**
	 * Setea el label del elemento
	 */
	public FormElementSelect setLabel(String label) {
		super.setLabel(label);

		return this;
	}
	
	public FormElementSelect disable(boolean flag){
		super.setDisable(flag);
		
		return this;
	}
	
	/**
	 * Si es requerido o no
	 */
	public FormElementSelect setRequired(boolean flag) {
		super.setRequired(flag);

		return this;
	}

	/**
	 * Agrega un validador al elemento
	 */
	public FormElementSelect addValidator(String validator) {
		super.addValidator(validator);

		return this;
	}

	/**
	 * Setea un decorador al elemento
	 */
	public FormElementSelect setDecorator(Decorator deco) {
		super.setDecorator(deco);

		return this;
	}

}
