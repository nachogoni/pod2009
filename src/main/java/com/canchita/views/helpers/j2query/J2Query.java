package com.canchita.views.helpers.j2query;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class J2Query {

	private List<J2QueryElement> elements = null;
	private String path;
	private boolean generated = false;

	/**
	 * Crea la instancia del handler del JJQuery
	 * 
	 * @param Ubicacion donde debe escribir el archivo unobstrusive
	 */
	public J2Query(String aPath) {
		elements = new ArrayList<J2QueryElement>();
		path = aPath;
	}
	
	public J2Query addElement(J2QueryElement elem){
		elements.add(elem);
		
		return this;
	}
	
	public J2Query addElements(List<J2QueryElement> elems){
		elements.addAll(elems);
		
		return this;
	} 
	
	public boolean isGenerated(){
		return generated;
	}
	
	public void generate(){
		generate(false);
	}
	
	public void regenerate(){
		generated = true;
	}
	
	public void generate(boolean force){
		if (generated && force == false)
			return;
		
		generated = true;
		
		StringBuffer export = new StringBuffer();
		//Init
		export.append("$(document).ready( function() {\n");
		
		for(J2QueryElement e : elements ){
			export.append(e.getCode() + "\n");
		}
		
		export.append("});");
		
		//Veo que exista el archivo, si no existe lo creo
		try{
			File f = new File(path);
			f.createNewFile();
		}catch (Exception e) {
			//TODO: Mandar a Log4J
			e.printStackTrace();
		}
		
		try{
			//Abro el archivo
			FileOutputStream file = new FileOutputStream(path, false);
			//Creo el StreamWriter
			OutputStreamWriter out = new OutputStreamWriter(file);
			//Escribo
			out.write(export.toString());
			//Salgo
			out.close();
			file.close();
		}catch (Exception e) {
			// TODO: Mandar a Log4J 
			e.printStackTrace();
		}
			
		return;
	}
}
