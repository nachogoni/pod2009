package com.canchita.controller.complex;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.service.ComplexService;
import com.canchita.views.helpers.FormHandler;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;

/**
 * Servlet implementation class AddComplex
 */
public class AddComplex extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(AddComplex.class.getName());
	private FormHandler formulario;

	public AddComplex() {
		formulario = new FormAddComplex();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("GET request");

		/*Get Form*/
		formulario = new FormAddComplex();

		/* Form is sent to the view*/
		request.setAttribute("formulario", this.formulario);
		
		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

		logger.debug("Saliendo del controlador");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("POST request");
		
		/*Errors from the past are deleted.*/
		this.formulario.unsetErrors();

		/*Load form with request values*/
		this.formulario.loadValues(request);
		
		if (!this.formulario.isValid())
		{
			logger.debug("Formulario inválido");
			request.setAttribute("formulario", formulario);
			UrlMapper.getInstance().forwardFailure(this, request, response, UrlMapperType.POST);
			return;
		}
		else
		{
		
		ComplexService addService = new ComplexService();

		
		//TODO: Migrar a ComplexForm
		//TODO: Arreglar el manejo de excepcion y redireccionar a pagina de error.
		ErrorManager error = new ErrorManager();

		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		String zipCode = request.getParameter("zipCode");
		String town = request.getParameter("town");
		String state = request.getParameter("state");
		String neighbourhood = request.getParameter("neighbourhood");
		String country = request.getParameter("country");
		String address = request.getParameter("address");

		
		if (name == null) {
			error.add("Falta el nombre del Complejo");
		}	
		if(address == null){
			error.add("Falta la direccion del complejo");
		}
		if(town == null){
			error.add("Falta la cuidad donde se encuentra el complejo");
		}
		if(state == null){
			error.add("Falta la provincia/estado donde se encuentra el complejo");
		}		


		
		Integer booking = null;
		Integer deposit = null; 
		Integer pay = null; 
		Integer downDeposit = null;
		Integer downBooking = null;

		try{
		
			booking = Integer.parseInt(request.getParameter("booking"));
			deposit = Integer.parseInt(request.getParameter("deposit"));
			pay = Integer.parseInt(request.getParameter("pay"));
			downBooking = Integer.parseInt(request.getParameter("downBooking"));
			downDeposit = Integer.parseInt(request.getParameter("downDeposit"));
		} catch (NumberFormatException nfe) {
			error.add("Valores para el sistema de puntos incorrectos");
		}

		if (error.size() != 0) {
			logger.debug("Error en el formulario");
			request.setAttribute("formulario", formulario);
			this.failure(request, response, error);
			return;
		}

		Integer depositLimit = null;
		Integer bookingLimit = null;
		
		try{
			
			depositLimit = Integer.parseInt(request.getParameter("depositLimit"));
			bookingLimit = Integer.parseInt(request.getParameter("bookingLimit"));
		} catch (NumberFormatException nfe) {
			error.add("Valores para el sistema de reservas incorrectos");
		}

		if (error.size() != 0) {
			logger.debug("Error en el formulario");
			request.setAttribute("formulario", formulario);
			this.failure(request, response, error);
			return;
		}
		
		try{
			Long id = addService.saveComplex(name, description, address, zipCode,
											neighbourhood, town, state, country);
			addService.addScoreSystem(id, booking, deposit, pay, downBooking,
																downDeposit);
			
			addService.addExpiration(id, bookingLimit, depositLimit);
		}
		catch(ElementExistsException ee) {
			
			error.add(ee);

		} catch (PersistenceException e) {
			error.add(e);
		}
		catch(IllegalArgumentException e) {
			error.add(e);
		}
		
		if (error.size() != 0) {
			logger.debug("Error al guardar el complejo");
			request.setAttribute("formulario", formulario);
			this.failure(request, response, error);
			return;
		}

		
		UrlMapper.getInstance().redirectSuccess(this, request, response,
													UrlMapperType.POST);
		
		}
	}
	
	private void failure(HttpServletRequest request,
			HttpServletResponse response, ErrorManager error)
			throws ServletException, IOException {
		request.setAttribute("errorManager", error);

		UrlMapper.getInstance().forwardFailure(this, request, response,
				UrlMapperType.POST);

	}

}