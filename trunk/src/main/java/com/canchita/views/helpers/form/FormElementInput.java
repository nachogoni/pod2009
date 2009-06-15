package com.canchita.views.helpers.form;

import java.util.ArrayList;
import java.util.List;

import com.canchita.views.helpers.j2query.J2QueryMultipleData;

public class FormElementInput extends FormElement {
	protected boolean multipleData;
	protected String idButton;
	protected ArrayList<FormElementInput> subelements = new ArrayList<FormElementInput>();

	public FormElementInput(String type, String name) {
		super(type, name);
		multipleData = false;
	}
	
	public void addSubElement(String value){
		FormElementInput enew = null;
		enew = new FormElementInput(this.type, this.name);
		enew.setLabel(this.label)
			.setValue(value);
		// Le anexo los validadores del padre
		/*enew.validators = validators;
		enew.validatorValues = validatorValues;*/
		this.subelements.add(enew);
	}

	/**
	 * Retorna si el elemento puede tener multiples ocurrencias
	 * 
	 * @return Boolean
	 */
	public boolean isMultipleData() {
		return multipleData;
	}

	/**
	 * Devuelve un listado de todos los values que posee el elemento En caso de
	 * ser multiple devuelve todos Sino devuelve su valor
	 * 
	 * @return List<String>
	 */
	public List<String> getValues() {
		List<String> data = new ArrayList<String>();

		// Agrego el valor del elemento
		data.add(this.getValue());

		for (FormElementInput e : subelements)
			data.add(e.getValue());

		return data;
	}

	@Override
	/*
	 * * Setea el id del elemento
	 */
	public FormElementInput setId(String id) {
		super.setId(id);

		return this;
	}

	/**
	 * Habilita que el elemento pueda tener multiples ocurrencias
	 * 
	 * @param id
	 *            del Boton que se genera
	 * @return
	 */
	public FormElementInput setMultipleData(String idBoton) {
		multipleData = true;
		idButton = idBoton;

		subelements = new ArrayList<FormElementInput>();

		return this;
	}

	@Override
	public FormElementInput addValidator(String validator, String param) {
		super.addValidator(validator, param);

		return this;
	}

	protected void addSubElement(FormElementInput e) {
		subelements.add(e);
	}

	protected String genInput() {
		String strclass = "";

		if (!this.deco.getSclass().equals("")) {
			strclass = String.format(" class=\"%s\"", this.deco.getSclass());
		}

		return String
				.format(
						"<div id='div%s'><input id='%s' type='%s' name='%s' value='%s' %s></div>",
						super.name, super.id, super.type, super.name,
						super.value, strclass);
	}

	@Override
	public FormElementInput setValue(String value) {
		// TODO Auto-generated method stub
		super.setValue(value);

		return this;
	}

	@Override
	public FormElementInput setLabel(String label) {
		super.setLabel(label);

		return this;
	}

	@Override
	public FormElementInput setRequired(boolean flag) {
		super.setRequired(flag);

		return this;
	}

	@Override
	public FormElementInput addValidator(String validator) {
		// TODO Auto-generated method stub
		super.addValidator(validator);

		return this;
	}

	public String toString() {
		String ret = "";

		ret = super.genLabel() + this.genInput();

		if (!super.deco.getFieldset().isEmpty()) {
			ret = String.format("<fieldset><legend> %s</legend>%s</fieldset>",
					this.name, ret);
		}

		// cuando hace el toString si esta habilitado el multiData agrego el JS
		if (multipleData) {
			this.addJ2QueryElement(new J2QueryMultipleData(this.idButton, "div"
					+ this.name, ret));
			ret += String
					.format(
							"<div id='%s' style='position:relative; top:-2em; left:23em;'>+</div>",
							this.idButton);

			for (FormElementInput e : subelements) {
				ret += e.toString();
			}
		}
		return ret;

	}

	@Override
	public FormElementInput setDecorator(Decorator deco) {
		// TODO Auto-generated method stub
		super.setDecorator(deco);

		return this;
	}

}
