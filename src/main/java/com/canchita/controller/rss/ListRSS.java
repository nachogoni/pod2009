package com.canchita.controller.rss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.PersistenceException;
import com.canchita.service.ComplexService;
import com.canchita.service.ComplexServiceProtocol;

/**
 * Servlet implementation class ListRSS
 */
public class ListRSS extends GenericServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListRSS() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("GET request");

		String scheme = request.getScheme(); // http
		String serverName = request.getServerName(); // hostname.com
		int serverPort = request.getServerPort(); // 80
		String contextPath = request.getContextPath(); // /mywebapp
		String url = scheme + "://" + serverName + ":" + serverPort + contextPath;

		String neighbourhood = request.getParameter("neighbourhood");
		String baseURL = url;
		
		List<String> rsss = new ArrayList<String>();
		
		rsss.add("LastFields");
		rsss.add("DeadBooking");

		ComplexServiceProtocol complexService = new ComplexService();
		ErrorManager errorManager = new ErrorManager();
		
		Collection<String> neighbourhoods = null;
		
		try {
			
			neighbourhoods = complexService.getNeighbourhoods();
			
		} catch (PersistenceException e) {
			errorManager.add(e);
			request.setAttribute("searchError", errorManager);
			logger.error("Error en la búsqueda de barrios");
			UrlMapper.getInstance().forwardFailure(this, request, response, UrlMapperType.GET);
			return;	
		}
		
		request.setAttribute("rsss", rsss);
		request.setAttribute("baseURL", baseURL);
		
		request.setAttribute("neighbourhood", neighbourhood);

		request.setAttribute("neighbourhoods", neighbourhoods);

		UrlMapper.getInstance().forwardSuccess(this, request, response, UrlMapperType.GET);
		
		return;
		
	}

}
