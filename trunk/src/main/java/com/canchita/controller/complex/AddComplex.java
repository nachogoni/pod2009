package com.canchita.controller.complex;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.InvalidScheduleException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.service.ComplexService.ComplexBuilder;
import com.canchita.views.helpers.form.FormHandler;

/**
 * Servlet implementation class AddComplex
 */
public class AddComplex extends GenericServlet {
	private static final long serialVersionUID = 1L;

	private FormHandler formulario;

	public AddComplex() {
		formulario = new FormAddComplex();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// TODO BORRAME
		// Testeos de UserDB.java
		// try {
		// Registered lala = UserDB.getInstance().getByUserName("pepe");
		// System.out.println(lala);
		// } catch (ElementNotExistsException e) {
		// System.out.println("El usuario no existe.");
		// }

		// try {
		// Registered lala = UserDB.getInstance().getByUserName("pepe");
		// UserDB.getInstance().delete(lala);
		// } catch (ElementNotExistsException e) {
		// System.out.println("El usuario no existe");
		// }

		// Administrator lala = new Administrator();
		// lala.setUsername("admin");
		// lala.setPassword("admin");
		// lala.setEmail("admin@email.com");
		// lala.setEmail("admin@email2.com");
		// try {
		// UserDB.getInstance().save(lala);
		// } catch (PersistenceException e) {
		// System.out.println("Fallo el insert");
		// }

		// Registered lala = null;
		// try {
		// lala = UserDB.getInstance().getByUserName("mili2");
		// System.out.println(lala);
		//			
		// } catch (ElementNotExistsException e) {
		// System.out.println("El usuario no existe.");
		// }
		//
		// lala.setUsername("mili182");
		// lala.setPassword("passNueva");
		// lala.setScore(182L);
		// lala.setNotifyBeforeExpiration(182L);
		//
		// try {
		// UserDB.getInstance().update(lala);
		// } catch (ElementNotExistsException e) {
		// System.out.println("El usuario no existe.");
		// e.printStackTrace();
		// } catch (PersistenceException e) {
		// System.out.println("Persistence!.");
		// e.printStackTrace();
		// }

		// Registered lala = null;
		// try {
		// lala = UserDB.getInstance().getByUserName("mili182");
		// System.out.println(lala);
		//			
		// } catch (ElementNotExistsException e) {
		// System.out.println("El usuario no existe.");
		// }
		//
		// try {
		// UserDB.getInstance().updateEmail(lala, "cambio@ccc.com",
		// "m@mili.com");
		// } catch (Exception e) {
		// System.out.println("Falló update de email.");
		// e.printStackTrace();
		// }

		// Administrator lala = null;
		// try {
		// lala = UserDB.getInstance().getByAdminName("admin");
		// System.out.println(lala);
		//			
		// } catch (ElementNotExistsException e) {
		// System.out.println("El admin no existe.");
		// }

		// Collection<Administrator> admins =
		// UserDB.getInstance().getAllAdmins();
		// System.out.println("Cantidad de admins: " + admins.size());
		// for (Administrator admin : admins) {
		// System.out.println(admin);
		// }
		//
		// Collection<CommonUser> users = UserDB.getInstance().getAllUsers();
		// System.out.println("Cantidad de users: " + users.size());
		// for (CommonUser user : users) {
		// System.out.println(user);
		// }
		// Administrator lala = null;
		// try {
		// lala = UserDB.getInstance().getByAdminName("admin1");
		// UserDB.getInstance().addEmail(lala, "mailUltranuevo@new.com");
		// System.out.println(lala);
		// } catch (Exception e) {
		// System.out.println("Error al agregar email.");
		// }
//		Field field = null;
//		try {
//			field = FieldDB.getInstance().getById(1L);
//		} catch (ElementNotExistsException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		CommonUser user = null;
//		try {
//			user = UserDB.getInstance().getCommonUserById(1L);
//		} catch (ElementNotExistsException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		DateTime start = new DateTime();
//		DateTime end = new DateTime().plusHours(2);
//		Schedule sched = new Schedule(start, end);		
//
//		Booking lala = new Booking(1L, field, user, 0, sched);
//		
//		try {
//			BookingDB.getInstance().save(lala);
//		} catch (PersistenceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		 
		 
		// TODO BORRAME

		logger.debug("GET request");

		/* Get Form */
		formulario = new FormAddComplex();

		/* Form is sent to the view */
		request.setAttribute("formulario", this.formulario);

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

		logger.debug("Saliendo del controlador");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("POST request");

		/* Errors from the past are deleted. */
		this.formulario.unsetErrors();

		/* Load form with request values */
		this.formulario.loadValues(request);

		if (!this.formulario.isValid()) {
			logger.debug("Formulario inválido");
			request.setAttribute("formulario", formulario);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
		} else {

			// TODO: Migrar a ComplexForm
			// TODO: Arreglar el manejo de excepcion y redireccionar a pagina de
			// error.
			ErrorManager error = new ErrorManager();

			String name = request.getParameter("name");
			String description = request.getParameter("description");

			String zipCode = request.getParameter("zipCode");
			String town = request.getParameter("town");
			String state = request.getParameter("state");
			String neighbourhood = request.getParameter("neighbourhood");
			String country = request.getParameter("country");
			String address = request.getParameter("address");

			if (name == null) {
				error.add("Falta el nombre del Complejo");
			}
			if (address == null) {
				error.add("Falta la direccion del complejo");
			}
			if (town == null) {
				error.add("Falta la cuidad donde se encuentra el complejo");
			}
			if (state == null) {
				error
						.add("Falta la provincia/estado donde se encuentra el complejo");
			}

			if (error.size() != 0) {
				logger.debug("Error en el formulario");
				request.setAttribute("formulario", formulario);
				this.failure(request, response, error);
				return;
			}

			Integer depositLimit = null;
			Integer bookingLimit = null;

			try {

				depositLimit = Integer.parseInt(request
						.getParameter("depositLimit"));
				bookingLimit = Integer.parseInt(request
						.getParameter("bookingLimit"));
			} catch (NumberFormatException nfe) {
				error.add("Valores para el sistema de reservas incorrectos");
			}

			if (error.size() != 0) {
				logger.debug("Error en el formulario");
				request.setAttribute("formulario", formulario);
				this.failure(request, response, error);
				return;
			}

			ArrayList<DateTime> schedule = null;
			String[] daysOfWeek = { "fecha_lunes_inicio", "fecha_lunes_fin",
					"fecha_martes_inicio", "fecha_martes_fin",
					"fecha_miercoles_inicio", "fecha_miercoles_fin",
					"fecha_jueves_inicio", "fecha_jueves_fin",
					"fecha_viernes_inicio", "fecha_viernes_fin",
					"fecha_sabado_inicio", "fecha_sabado_fin",
					"fecha_domingo_inicio", "fecha_domingo_fin" };

			try {

				schedule = new ArrayList<DateTime>();

				DateTimeFormatter parser = DateTimeFormat.forPattern("HH:mm"); 
				String aDay = null;
				
				for (int i = 0; i < daysOfWeek.length; i++) {

					aDay = request.getParameter(daysOfWeek[i]);
					schedule.add(parser.parseDateTime(aDay));
					aDay = request.getParameter(daysOfWeek[i++]);
					schedule.add(parser.parseDateTime(aDay));
					
				}

				// TODO Atrapar la excepcion posta (o crearla si no hay nada
				// adecuado)
			} catch (NullPointerException e) {
				error.add("Faltan valores para los horarios");
			} catch (Exception e) {
				error.add("Valores para los horarios incorrectos");
			}
			try {
				ComplexBuilder.Build(name, description, address, zipCode,
						neighbourhood, town, state, country);

				ComplexBuilder.addExpiration(bookingLimit, depositLimit);

				ComplexBuilder.addTimeTable(schedule.get(0), schedule.get(1),
						schedule.get(2), schedule.get(3), schedule.get(4),
						schedule.get(5), schedule.get(6), schedule.get(7),
						schedule.get(8), schedule.get(9), schedule.get(10),
						schedule.get(11), schedule.get(12), schedule.get(13));
				ComplexBuilder.saveComplex();

				//TODO loguear los errores
			} catch (ElementExistsException ee) {
				error.add(ee);
				ee.printStackTrace();
			} catch (PersistenceException e) {
				error.add(e);
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				error.add(e);
				e.printStackTrace();
			} catch (InvalidScheduleException e) {
				error.add(e);
				e.printStackTrace();
			}catch (Exception e) {
				error.add(e);
				e.printStackTrace();
			}

			if (error.size() != 0) {
				logger.debug("Error al guardar el complejo");
				request.setAttribute("formulario", formulario);
				this.failure(request, response, error);
				return;
			}

			UrlMapper.getInstance().redirectSuccess(this, request, response,
					UrlMapperType.POST);

		}
	}

	private void failure(HttpServletRequest request,
			HttpServletResponse response, ErrorManager error)
			throws ServletException, IOException {
		request.setAttribute("errorManager", error);

		UrlMapper.getInstance().forwardFailure(this, request, response,
				UrlMapperType.POST);

	}

}
