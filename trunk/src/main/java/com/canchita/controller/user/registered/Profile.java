package com.canchita.controller.user.registered;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.FormHolder;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.user.Registered;
import com.canchita.service.UserService;
import com.canchita.service.UserServiceProtocol;
import com.canchita.views.helpers.FormHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Profile
 */
public class Profile extends GenericServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see GenericServlet#GenericServlet()
     */
    public Profile() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
		Registered user = this.getUser(request);
		
		UserServiceProtocol userService = new UserService();
		
		List<String> mails = userService.getMails(user);


		FormHandler form = new FormProfile(mails);

		request.setAttribute("formulario", form);
		
		//We save the form to compare it later
		
		FormHolder.getInstance().saveForm(this, request.getSession().getId(), form);
		
		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);
		}catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		logger.debug("POST request");
		
		FormProfile oldForm = (FormProfile) FormHolder.getInstance().getForm(this, request.getSession().getId());
		
		FormProfile form = new FormProfile(oldForm.getMailAmount());
		
		/*Errors from the past are deleted.*/
		form.unsetErrors();

		/*Load form with request values*/
		form.loadValues(request);
		
		if (!form.isValid())
		{
			logger.debug("Formulario inválido");
			request.setAttribute("profileForm", form);
			UrlMapper.getInstance().forwardFailure(this, request, response, UrlMapperType.POST);
			return;
		}
		else
		{
		ErrorManager error = new ErrorManager();

		Map<String,String> mailsToUpdate = oldForm.getUpdatedMails(form);
		
		Registered user = this.getUser(request);
		
		UserServiceProtocol userService = new UserService();
		
		userService.updateMails(user,mailsToUpdate);
		
		FormHolder.getInstance().removeForm(this, request.getSession().getId());
		
		Map<String,String> map = new HashMap<String, String>();
		
		map.put("update", "true");
		
		UrlMapper.getInstance().redirectSuccess(this, request, response,
													UrlMapperType.POST,map);
		
		}
		
	}

}
