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
	
	public Long saveComplex(String name, String description, String address, String zipCode, String neighbourhood, String town, String state, String country);
	
	public void updateComplex(Complex aComplex);
	
	public void addScoreSystem(Long id, Integer booking, Integer deposit, Integer pay, Integer downBooking, Integer downDeposit);
	
	public Complex getById(Long id);
	
//	private String name;
//	private Place place;
//	private String description;
//	private Calendar timeTable;
//	private ScoreSystem scoreSystem;
//	private List<Field> fields;
//	private Expiration expiration;
//	private Long id;
	
}
