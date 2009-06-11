package com.canchita.controller.helper;

import java.util.HashMap;
import java.util.Map;

import com.canchita.controller.GenericServlet;
import com.canchita.views.helpers.form.FormHandler;

public class FormHolder {

	private static final FormHolder INSTANCE = new FormHolder();
	private Map<String, FormHandler> holder;
	
	FormHolder() {
		
		this.holder = new HashMap<String, FormHandler>();
		
	}
	
	public static FormHolder getInstance() {
		return INSTANCE;
	}

	public void saveForm(GenericServlet servlet, String sessionId, FormHandler form) {
		holder.put(sessionId + servlet.getServletName(), form);
		
	}
	
	public FormHandler getForm(GenericServlet servlet, String sessionId) {
		return holder.get(sessionId + servlet.getServletName() );
	}

	public void removeForm(GenericServlet servlet, String sessionId) {
		holder.remove(sessionId + servlet.getServletName() );
	}
	
}
