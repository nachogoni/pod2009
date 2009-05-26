package com.canchita.model.field;

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
public enum FloorType {

	GRASS,ARTIFICIAL_GRASS,CONCRETE;
	
	public String toString() {
		if (this == GRASS) {
			return "Césped";
		} else if (this == ARTIFICIAL_GRASS) {
			return "Césped Sintético";
		} else if (this ==CONCRETE) {
			return "Concreto";
		} else {
			return "Inválido";
		}
	}
	
}
