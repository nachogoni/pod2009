package com.canchita.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.canchita.controller.security.ACLCanchita;
import com.canchita.model.user.User;

public class ACLFilter extends FilterWithLog implements Filter {

	//TODO ver esto!!!
	private static final String FORBIDDEN = "/error/403";
	private FilterConfig filterConfig;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		// if the ServletRequest is an instance of HttpServletRequest
		if (servletRequest instanceof HttpServletRequest) {
			// cast the object
			HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

			// Let's check if the user has access
			if (!this.hasAccess(httpServletRequest)) {
				logger.debug("El usuario actual no tiene permiso para acceder");
				 httpServletRequest.getRequestDispatcher(FORBIDDEN).forward(
				 servletRequest, servletResponse);

				return;
			}
			// continue request
			filterChain.doFilter(httpServletRequest, servletResponse);

		} else {
			// otherwise, continue on in the chain with the ServletRequest and
			// ServletResponse objects
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	private boolean hasAccess(HttpServletRequest httpServletRequest) {

		logger.debug("Entra en hasAccess");
		User user = (User) httpServletRequest.getSession().getAttribute("user");

		String url = httpServletRequest.getRequestURL().toString();

		logger.debug("url: " + url);

		String search = "/tp-pod";

		int index = url.indexOf(search);

		if (index == -1) {
			return true;
		}

		String result = url.substring(index + search.length());

		logger.debug("result: " + result);
		String[] split = result.split("\\?");

		String object = split[0];
		logger.debug("usuario: " + user);
		logger.debug("objeto: " + object);

		return ACLCanchita.getInstance().isAuthorized(user, object);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

	}

}
