package com.canchita.web;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.*;                                                         
import javax.servlet.http.*;

import com.canchita.DAO.ComplexMemoryMock;
import com.canchita.model.complex.Complex;
import com.canchita.model.user.CommonUser;

public class PruebaServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws ServletException, IOException {
    
    	Complex unComplejo = new Complex();
    	ComplexMemoryMock aDao = new ComplexMemoryMock();
    	
    	unComplejo.setId(new Long(1));
    	unComplejo.setName("carlitos");
    	aDao.save(unComplejo);
    	ArrayList<Complex> aMap = (ArrayList<Complex>) aDao.getAll();
    	
    	request.setAttribute("complejos", aMap);
    	request.getRequestDispatcher("/index.jsp").forward(request, response);


    }
}
