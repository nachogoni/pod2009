package com.canchita.filter;

import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public abstract class FilterWithLog {

	private static final String LOG4J_PROPERTIES = "log4j.properties";
	protected Logger logger;

	protected FilterWithLog() {
		super();
		URL url = Thread.currentThread().getContextClassLoader().getResource(
				LOG4J_PROPERTIES);
		PropertyConfigurator.configure(url);

		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
}
