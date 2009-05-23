package com.canchita.views.helpers;

public class Decorator {
	private String tag;
	private String label;
	private String sclass;
	private String id;
	private String fieldName;
	
	public Decorator() {
		tag = "";
		label = "";
		sclass = "";
		fieldName = "";
	}

	public String getTag() {
		return tag;
	}

	public Decorator setTag(String tag) {
		this.tag = tag;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public Decorator setLabel(String label) {
		this.label = label;
		return this;
	}

	public String getSclass() {
		return sclass;
	}

	public Decorator setSclass(String sclass) {
		this.sclass = sclass;
		return this;
	}

	public String getId() {
		return id;
	}

	public Decorator setId(String id) {
		this.id = id;
		return this;
	}
	
	public String getFieldset() {
		return fieldName;
	}

	public Decorator setFieldset(String aName) {
		this.fieldName = aName;
		return this;
	}
	
	
}
