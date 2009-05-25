package com.canchita.service;

import java.util.Collection;
import java.util.List;

import com.canchita.model.booking.Expiration;
import com.canchita.model.complex.Complex;
import com.canchita.model.complex.ScoreSystem;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.field.Field;

public interface ComplexServiceProtocol {

	public Collection<Complex> listComplex();
	
	public Collection<Complex> listComplex(String filter) throws ValidationException;
	
	public void deleteComplex(Long id);
	
	public void saveComplex(String name, String description, String address, String zipCode, String neighbourhood, String town, String state, String country);
	
	public void updateComplex(Complex aComplex);
	
	public Complex getById(Long id);
	
//	private String name;
//	private Place place;
//	private String description;
//	private Calendar timeTable;
//	private ScoreSystem scoreSystem;
//	private List<Field> fields;
//	private Expiration expiration;
//	private Long id;
//	
//	private String address;
//	private String zipCode;
//	private String neighbourhood;
//	private String town;
//	private String state;
//	private String country;
//	private String latitude;
//	private String longitude;
//	private List<String> telephones;
	
}
