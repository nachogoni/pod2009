package com.canchita.views.helpers;

public class FormElementInclude extends FormElement {

	private String path;
	
	public FormElementInclude(String path) {
		super("include", "path");
		// TODO Auto-generated constructor stub
	}
	
	public FormElementInclude() {
		super("include", "path");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("<jsp:include page=\"%s\" flush=\"true\"/>", this.path);
	}

	public String getPath() {
		return path;
	}

	public FormElementInclude setPath(String path) {
		this.path = path;
		
		return this;
	}

}
