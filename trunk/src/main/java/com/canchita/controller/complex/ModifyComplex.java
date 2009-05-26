package com.canchita.controller.complex;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.service.ComplexService;

/**
 * Servlet implementation class ModifyComplex
 */
public class ModifyComplex extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyComplex() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ComplexService service = new ComplexService();

		Long id = null;

		try {
			id = Long.parseLong((request.getParameter("id")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
		
			request.setAttribute("complex", service.getById(id));
			UrlMapper.getInstance().forwardSuccess(this, request, response,
					UrlMapperType.GET);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComplexService modifyService = new ComplexService();
		
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
		Long id = null;
		
		try{
			id = Long.parseLong(request.getParameter("id"));
			booking = Integer.parseInt(request.getParameter("booking"));
			deposit = Integer.parseInt(request.getParameter("deposit"));
			pay = Integer.parseInt(request.getParameter("pay"));
			downBooking = Integer.parseInt(request.getParameter("downBooking"));
			downDeposit = Integer.parseInt(request.getParameter("downDeposit"));
		} catch (NumberFormatException nfe) {
			error.add("Valores para el sistema de puntos incorrectos");
		}

		if (error.size() != 0) {
			//this.failure(request, response, error);
			return;
		}

		modifyService.updateComplex(id,name, description, address, zipCode, neighbourhood, town, state, country);
		modifyService.addScoreSystem(id, booking, deposit, pay, downBooking, downDeposit);
		

		
		UrlMapper.getInstance().redirectSuccess(this, request, response, UrlMapperType.POST);
		
	}

}
