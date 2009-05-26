package com.canchita.controller.field;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.service.FieldService;

/**
 * Servlet implementation class DetaildedViewField
 */
public class DetaildedViewField extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		FieldService service = new FieldService();

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
		// TODO Auto-generated method stub
	}

}
