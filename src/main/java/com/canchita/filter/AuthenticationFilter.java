package com.canchita.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.canchita.service.UserService;

public class AuthenticationFilter extends FilterWithLog implements Filter {

	private FilterConfig filterConfig;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
									
				//if the ServletRequest is an instance of HttpServletRequest  
		         if(servletRequest instanceof HttpServletRequest) {  
		             //cast the object  
		             HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
		             this.setUser(httpServletRequest);
		             //continue request
		             filterChain.doFilter(httpServletRequest , servletResponse);
		         } else {  
		             //otherwise, continue on in the chain with the ServletRequest and ServletResponse objects  
		             filterChain.doFilter(servletRequest, servletResponse);  
		         }
	}

	private void setUser(HttpServletRequest httpServletRequest) {
		
		if( httpServletRequest.getSession().isNew()) {
			UserService userService = new UserService();
			
			httpServletRequest.getSession().setAttribute("user", userService.createGuest());
			
			//TODO Agregar el formulario de login para los headers o ver donde 
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

	}

}
