package com.canchita.controller.field;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.booking.Expiration;
import com.canchita.model.field.FloorType;
import com.canchita.service.ComplexService;
import com.canchita.service.FieldService;
import com.canchita.views.helpers.ComplexForm;
import com.canchita.views.helpers.FormHandler;

/**
 * Servlet implementation class addField
 */
public class addField extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private FormHandler formulario;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public addField() {
		super();
		formulario = new ComplexForm();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//TODO: Migrar a FieldForm
		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		FieldService addService = new FieldService();

		Long idComplex = -1L;
		Boolean hasRoof = false;
		FloorType floor = FloorType.CONCRETE;
		Expiration expiration = null;
		
		//TODO: Migrar a FieldForm
		//TODO: Arreglar el manejo de excepcion y redireccionar a pagina de error.
		ErrorManager error = new ErrorManager();

		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		try{
			idComplex = Long.getLong(request.getParameter("idComplex"));
		} catch (NumberFormatException nfe) {
			error.add("Complejo invalido");
		}
		
		try{
			hasRoof = Boolean.valueOf(request.getParameter("hasRoof"));
		} catch (Exception nfe) {
			error.add("Informacion sobre el tipo de techo invalida");
		}
		
		try{
			floor = FloorType.valueOf(request.getParameter("floor"));
		} catch (Exception nfe) {
			error.add("Informacion sobre el tipo de piso invalida");
		}
		
		//expiration = request.getParameter("expiration"); TODO:

		if (name == null) {
			error.add("Falta el nombre de la Cancha");
		}	
		/*if(idComplex == null){
			error.add("Falta el complejo");
		}
		if(hasRoof == null){
			error.add("Falta si la cancha tiene techo");
		}
		if(floor == null){
			error.add("Falta el tipo de piso de la cancha");
		}		
		if(expiration == null){
			error.add("Falta el tiempo de expiracion");
		}*/		

		if (error.size() != 0) {
			this.failure(request, response, error);
			return;
		}

		Long id = addService.saveField(name, description, idComplex, hasRoof, floor, expiration);
		
		UrlMapper.getInstance().redirectSuccess(this, request, response, UrlMapperType.POST);
		
	}
	
	private void failure(HttpServletRequest request,
			HttpServletResponse response, ErrorManager error)
			throws ServletException, IOException {
		request.setAttribute("errorManager", error);

		UrlMapper.getInstance().forwardFailure(this, request, response,
				UrlMapperType.GET);

	}

}