package com.canchita.controller.scoresystem;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.service.ScoreSystemService;

public class ShowScoreSystem extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowScoreSystem() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("GET request");

		ScoreSystemService service = new ScoreSystemService();

		ErrorManager error = new ErrorManager();

		try {
			logger.debug("Buscando detalles del sistema de puntos");
			request.setAttribute("scoreSystem", service.getScoreSystem());

		} catch (Exception e) {
			error.add(e);
			logger.error("Error buscando detalles del sistema de puntos");
		}

		if (error.size() != 0) {

			request.setAttribute("errorManager", error);

			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);

			return;
		}

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

	}
}
