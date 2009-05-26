package com.canchita.views.helpers;

public class FormElementInclude extends FormElement {

	private String path;
	
	public FormElementInclude(String path) {
		super("include", path);
	}
	
	public FormElementInclude() {
		super("include", "path");
	}
	
	@Override
	public String toString() {
		return String.format("<jsp:include page=\"%s\" flush=\"true\"/>",
				this.path);
	}

	public String getPath() {
		return this.path;
	}

	public FormElementInclude setPath(String path) {
		this.path = path;
		
		return this;
	}

}
