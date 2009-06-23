package com.canchita.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTimeZone;

public class GenericFilter extends FilterWithLog implements Filter {

	private FilterConfig filterConfig;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {

		      //if the ServletRequest is an instance of HttpServletRequest  
		         if(servletRequest instanceof HttpServletRequest) {  
		             
		        	 //Set defaultTimeZone
		        	 DateTimeZone.setDefault(DateTimeZone.UTC);
		        	 
		        	 //cast the object  
		             HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
		             
		             String search = "tp-pod";

		             httpServletRequest.setAttribute("baseURL", this.getBaseURL(httpServletRequest.getRequestURL(), search));
		             httpServletRequest.setAttribute("baseURI", this.getBaseURI(httpServletRequest.getRequestURI(), search));
		             
		             try {
		            	 filterChain.doFilter(httpServletRequest , servletResponse);
		             }catch(Throwable t) {
			        	 this.logger.fatal("Error fatal que llego hasta el filtro", t);
			         }
		         } else {  
		             //otherwise, continue on in the chain with the ServletRequest and ServletResponse objects  
		             filterChain.doFilter(servletRequest, servletResponse);  
		         }   	
	}

	private Object getBaseURI(String requestURI, String search) {
		return this.getBase(requestURI, search);
	}

	private Object getBaseURL(StringBuffer requestURL, String search) {
		return this.getBase(requestURL.toString(), search);
	}

	private Object getBase(String string, String search) {
		String result = null;
	    
        int index = string.indexOf(search);
        if( index != -1 ) {
       	 result = string.substring(0, index + search.length() );
       	 
        }
        
		return result;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

	}

}
