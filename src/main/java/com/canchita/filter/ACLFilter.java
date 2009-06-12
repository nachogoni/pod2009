package com.canchita.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.omg.CORBA.Request;

import com.canchita.controller.security.ACL;
import com.canchita.controller.security.ACLCanchita;
import com.canchita.model.user.User;
import com.canchita.service.UserService;

public class ACLFilter implements Filter {

	private static final String FORBIDDEN = "/WEB-INF/views/error/forbidden.jsp";
	private FilterConfig filterConfig;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

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
			if (! this.hasAccess(httpServletRequest)) {
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

		User user = (User) httpServletRequest.getSession().getAttribute("user");

		String url = httpServletRequest.getRequestURL().toString();

		String search = "/tp-pod";

		int index = url.indexOf(search);

		if (index == -1) {
			return true;
		}

		String result = url.substring(index + search.length());

		String[] split = result.split("\\?");

		String object = split[0];

		return ACLCanchita.getInstance().isAuthorized(user, object);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

	}

}
