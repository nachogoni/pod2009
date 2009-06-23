package com.canchita.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.canchita.jdbc.ConnectionPool;

public class JDBCListener implements ServletContextListener {

   public void contextInitialized(ServletContextEvent event) {
	   ConnectionPool.getInstance();
   }

   public void contextDestroyed(ServletContextEvent event) {
	  ConnectionPool.getInstance().cleanConnections();
   }
}