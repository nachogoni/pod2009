package com.canchita.controller.complex;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.service.ComplexService;

/**
 * Servlet implementation class DetailedViewComplex
 */
public class DetailedViewComplex extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Logger logger = Logger.getLogger(DetailedViewComplex.class.getName());
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailedViewComplex() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.debug("GET request");
		
		ComplexService service = new ComplexService();

		Long id = null;

		try {
			id = Long.parseLong((request.getParameter("id")));
		} catch (Exception e) {
			logger.error("Error leyendo id");
			e.printStackTrace();
		}

		try {
			logger.debug("Buscando detalles del complejo con id " + id);
			request.setAttribute("complex", service.getById(id));
			UrlMapper.getInstance().forwardSuccess(this, request, response,
					UrlMapperType.GET);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error buscando detalles del complejo con id " + id);
		}

	}

}
