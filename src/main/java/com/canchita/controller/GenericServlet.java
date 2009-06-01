package com.canchita.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.canchita.controller.admin.AdminHome;

/**
 * Servlet implementation class GenericServlet
 */
public class GenericServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected Logger logger;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenericServlet() {
        super();
        this.logger = Logger.getLogger(this.getClass().getName());
    }

}
