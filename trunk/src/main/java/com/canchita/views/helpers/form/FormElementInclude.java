package com.canchita.views.helpers.form;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FormElementInclude extends FormElement {

	private String path;

	public FormElementInclude(String aName, String apath) {
		super("include", aName);
		path = apath;
	}

	@Override
	public String toString() {
		String strLine, ret = "";

		try {
			//levanto el archivo y lo mando
			FileInputStream fstream = new FileInputStream(this.path);

			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			while ((strLine = br.readLine()) != null) {
				ret += strLine;
			}
			// Close the input stream
			in.close();

		} catch (Exception e) {// Catch exception if any
			//TODO:No se a donde tiene que ir
			System.out.println("Error: " + e.getMessage());
		}

		return super.genLabel() + ret;
	}

	public String getPath() {
		return this.path;
	}

	public FormElementInclude setPath(String path) {
		this.path = path;

		return this;
	}
	
	@Override
	public FormElementInclude setLabel(String label) {
		// TODO Auto-generated method stub
		super.setLabel(label);
		
		return this;
	}

}
