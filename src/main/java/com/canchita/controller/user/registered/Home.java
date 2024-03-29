package com.canchita.controller.user.registered;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;

/**
 * Servlet implementation class Home
 */
public class Home extends GenericServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see GenericServlet#GenericServlet()
     */
    public Home() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);
	}

}
