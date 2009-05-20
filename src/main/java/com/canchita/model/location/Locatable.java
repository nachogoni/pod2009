package com.canchita.model.location;

/**
 * 
 * @author Pablo Federico Abramowicz
 * @author Martín Esteban Cabral
 * @author Darío Maximiliano Gomez Vidal
 * @author Juan Ignacio Goñi
 * @author Martín Palombo
 * @author Carlos Manuel Sessa
 *
 */
public interface Locatable {

	/**
	 * 
	 * @return name of the locatable
	 */
	String getName();
	
	/**
	 * 
	 * @return place where it is located
	 */
	Place getPlace();
	
}
