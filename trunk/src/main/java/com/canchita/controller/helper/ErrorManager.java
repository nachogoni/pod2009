package com.canchita.controller.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ErrorManager {

	private List<String> errors;

	public ErrorManager() {
		this.errors = new ArrayList<String>();
	}
	
	public void add(String error) {
		this.errors.add(error);
	}
	
	public void add(Exception exception) {
		this.errors.add(exception.getMessage());
	}
	
	public String toString() {
		
		StringBuffer ret = new StringBuffer();

		
		for (String error : errors) {
			ret.append(error + "\n");
		}
		
		return ret.toString();
	}

	public int size() {
		return errors.size();
	}
	
	public Iterator<String> getErrors() {
		return this.errors.iterator();
	}

}
