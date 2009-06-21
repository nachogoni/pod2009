package com.canchita.controller.complex;

import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.DateTime;

import com.canchita.model.booking.Expiration;
import com.canchita.model.complex.Availability;
import com.canchita.model.complex.Calendar;
import com.canchita.model.complex.Complex;
import com.canchita.views.helpers.form.Decorator;
import com.canchita.views.helpers.form.FormElementButton;
import com.canchita.views.helpers.form.FormElementInput;
import com.canchita.views.helpers.form.FormHandler;
import com.canchita.views.helpers.form.Pair;
import com.canchita.views.helpers.j2query.J2QueryTimePicker;

public class FormAddComplex extends FormHandler {
	private static HashMap<String, String> days = new HashMap<String, String>();

	static {
		days.put("Lunes", "fecha_lunes_");
		days.put("Martes", "fecha_martes_");
		days.put("Miércoles", "fecha_miercoles_");
		days.put("Jueves", "fecha_jueves_");
		days.put("Viernes", "fecha_viernes_");
		days.put("Sábado", "fecha_sabado_");
		days.put("Domingo", "fecha_domingo_");
	}

	public FormAddComplex() {
		super();
		ArrayList<String> scomplejo = new ArrayList<String>();
		ArrayList<String> subicacion = new ArrayList<String>();

		ArrayList<String> slunes = new ArrayList<String>();
		ArrayList<String> smartes = new ArrayList<String>();
		ArrayList<String> smiercoles = new ArrayList<String>();
		ArrayList<String> sjueves = new ArrayList<String>();
		ArrayList<String> sviernes = new ArrayList<String>();
		ArrayList<String> ssabado = new ArrayList<String>();
		ArrayList<String> sdomingo = new ArrayList<String>();

		ArrayList<String> sexpiration = new ArrayList<String>();
		ArrayList<String> sconfirm = new ArrayList<String>();

		ArrayList<String> horas = new ArrayList<String>();

		for (int i = 0; i < 24; i++) {
			horas.add(String.format("%02d:%02d", i, ((i % 2) == 0) ? 0 : 30));
		}

		this.setName("Complejo")
			.enableJ2Query("src/main/webapp/js/complex/add/init.js")
			.setId("formComplex")
			//.enableJ2QueryValidation()
			.setMethod("post");

		this.addElement(new FormElementInput("text", "name")
			.setLabel("Nombre")
			.setId("idNombre")
			.setRequired(true)
			.addValidator("MaxLength","20")
			.addValidator("IsAlphaNumS")
			.addJ2QueryTooltip("Nombre del Complejo"));

		this.addElement(new FormElementInput("text", "description")
			.setLabel("Descripci&oacute;n")
			.setId("idDescripcion")
			.addValidator("MaxLength", "100")
			.setRequired(false))
			.addJ2QueryTooltip("Descripci&oacute;n del Complejo");

		this.addElement(new FormElementInput("text", "address")
			.setLabel("Direcci&oacute;n")
			.setId("idDireccion")
			.setRequired(true)
			.addValidator("MaxLength", "50")
			.addValidator("IsAddress")
			.addJ2QueryTooltip("Direcci&oacute;n del Complejo"));

		this.addElement(new FormElementInput("text", "neighbourhood")
			.setLabel("Barrio")
			.setId("idNeighbourhood")
			.setRequired(true)
			.addValidator("MaxLength", "50")
			.addValidator("IsAlphaNumS")
			.addJ2QueryTooltip("Barrio del Complejo"));

		this.addElement(new FormElementInput("text", "town")
			.setLabel("Ciudad")
			.setRequired(true)
			.addValidator("IsAlphaNumS")
			.setId("idTown")
			.addValidator("MaxLength", "50")
			.addJ2QueryTooltip("Ciudad del Complejo"));

		this.addElement(new FormElementInput("text", "state")
			.setLabel("Provincia")
			.setRequired(true)
			.addValidator("IsAlphaNumS")
			.addValidator("MaxLength", "50").setId("idState")
			.addJ2QueryTooltip("Provincia del Complejo"));

		this.addElement(new FormElementInput("text", "zipcode")
			.setLabel("C&oacute;digo Postal")
			.setRequired(true)
			.addValidator("IsNumeric")
			.setId("idZipCode")
			.addJ2QueryTooltip("C&oacute;digo Postal del Complejo"));

		this.addElement(new FormElementInput("text", "latitude")
			.setLabel("Latitud")
			.setId("idLatitude")
			.addJ2QueryTooltip("Latitud del Complejo"));

		this.addElement(new FormElementInput("text", "longitude")
			.setLabel("Longitud")
			.setId("idLongitude")
			.addJ2QueryTooltip("Longitud del Complejo"));

		this.addElement(new FormElementInput("text", "telephone")
			.setLabel("Tel&eacute;fono")
			.setId("idTelephone")
			.setRequired(true)
			.addValidator("IsTelephone")
			.setMultipleData("multiplePhone")
			.addJ2QueryTooltip("Tel&eacute;fono del Complejo"));

		this.addElement(new FormElementInput("text", "country")
			.setRequired(true)
			.addValidator("IsAlphaNumS")
			.addValidator("MaxLength","50")
			.setId("idCountry")
			.setLabel("País")
			.addJ2QueryTooltip("Pa&iacute;s del Complejo"));

		ArrayList<String> diasDeSemana = new ArrayList<String>();
		diasDeSemana.add("lunes");
		diasDeSemana.add("martes");
		diasDeSemana.add("miercoles");
		diasDeSemana.add("jueves");
		diasDeSemana.add("viernes");
		diasDeSemana.add("sabado");
		diasDeSemana.add("domingo");

		/* cargo los horarios */
		for (String e : diasDeSemana) {
			this.addElement(new FormElementInput("text", String.format("fecha_%s_inicio", e))
				.setLabel("Inicio")
				.setRequired(true)
				.setValue("00:00")
				.setId(String.format("idfecha_%s_inicio", e))
				.addValidator("IsHourMinute", "Horario de inicio del " + e)
				.addJ2QueryElement(new J2QueryTimePicker(String.format("idfecha_%s_inicio", e)))
				.addJ2QueryTooltip("Hora de inicio"));

			this.addElement(new FormElementInput("text", String.format("fecha_%s_fin", e))
				.setLabel("Cierre")
				.setRequired(true)
				.setValue("00:00")
				.setId(String.format("idfecha_%s_fin", e))
				.addValidator("IsHourMinute", "Horario de cierre del " + e)
				.addJ2QueryElement(new J2QueryTimePicker(String.format("idfecha_%s_fin", e)))
				.addJ2QueryTooltip("Hora de cierre"));
		}

		this.addElement(new FormElementInput("text", "depositLimit").setLabel(
				"Límite de seña").setId("depositLimit").setRequired(true)
				.addValidator("IsNumeric"));

		this.addElement(new FormElementInput("text", "bookingLimit").setLabel(
				"L&iacute;mite de reserva").setId("bookingLimit").setRequired(
				true).addValidator("IsNumeric"));

		Decorator decoBotones = new Decorator().setSclass("submit-go");

		this.addElement(new FormElementButton("submit", "submit").setValue(
				"Confirmar").setDecorator(decoBotones));

		this.addElement(new FormElementButton("reset", "reset").setValue(
				"Reset").setDecorator(decoBotones));

		/* agrego los grupos */
		scomplejo.add("name");
		scomplejo.add("description");
		this.addDisplayGroup(scomplejo, "Complejo");

		subicacion.add("address");
		subicacion.add("neighbourhood");
		subicacion.add("town");
		subicacion.add("state");
		subicacion.add("zipcode");
		subicacion.add("latitude");
		subicacion.add("longitude");
		subicacion.add("telephone");
		subicacion.add("country");
		this.addDisplayGroup(subicacion, "Ubicaci&oacute;n");

		slunes.add("fecha_lunes_inicio");
		slunes.add("fecha_lunes_fin");
		this.addDisplayGroup(slunes, "Lunes");

		smartes.add("fecha_martes_inicio");
		smartes.add("fecha_martes_fin");
		this.addDisplayGroup(smartes, "Martes");

		smiercoles.add("fecha_miercoles_inicio");
		smiercoles.add("fecha_miercoles_fin");
		this.addDisplayGroup(smiercoles, "Mi&eacute;rcoles");

		sjueves.add("fecha_jueves_inicio");
		sjueves.add("fecha_jueves_fin");
		this.addDisplayGroup(sjueves, "Jueves");

		sviernes.add("fecha_viernes_inicio");
		sviernes.add("fecha_viernes_fin");
		this.addDisplayGroup(sviernes, "Viernes");

		ssabado.add("fecha_sabado_inicio");
		ssabado.add("fecha_sabado_fin");
		this.addDisplayGroup(ssabado, "S&aacute;bado");

		sdomingo.add("fecha_domingo_inicio");
		sdomingo.add("fecha_domingo_fin");
		this.addDisplayGroup(sdomingo, "Domingo");

		sexpiration.add("depositLimit");
		sexpiration.add("bookingLimit");
		this.addDisplayGroup(sexpiration, "Se&ntilde;a y Pago de Reservas");

		sconfirm.add("submit");
		sconfirm.add("reset");
		this.addDisplayGroup(sconfirm, "Confirmaci&oacute;n");

	}

	public FormAddComplex(Complex aComplex, Expiration policy) {
		this();

		String aux;
		Integer iaux;
		ArrayList<Pair<String, String>> data = new ArrayList<Pair<String, String>>();

		aux = aComplex.getName();
		if (aux != null)
			data.add(new Pair<String, String>("name", aux));

		aux = aComplex.getDescription();
		if (aux != null)
			data.add(new Pair<String, String>("description", aux));

		aux = aComplex.getPlace().getAddress();
		if (aux != null)
			data.add(new Pair<String, String>("address", aux));

		aux = aComplex.getPlace().getNeighbourhood();
		if (aux != null)
			data.add(new Pair<String, String>("neighbourhood", aux));

		aux = aComplex.getPlace().getTown();
		if (aux != null)
			data.add(new Pair<String, String>("town", aux));

		aux = aComplex.getPlace().getState();
		if (aux != null)
			data.add(new Pair<String, String>("state", aux));

		aux = aComplex.getPlace().getZipCode();
		if (aux != null)
			data.add(new Pair<String, String>("zipcode", aux));

		aux = aComplex.getPlace().getLatitude();
		if (aux != null)
			data.add(new Pair<String, String>("latitude", aux));

		aux = aComplex.getPlace().getLongitude();
		if (aux != null)
			data.add(new Pair<String, String>("longitude", aux));

		if (!aComplex.getPhones().isEmpty()) {
			// Como tiene multiples los agrego
			for (int i = 0; i < aComplex.getPhones().size(); i++) {
				aux = aComplex.getPhones().get(i);
				data.add(new Pair<String, String>("telephone", aux));
			}
		}

		Calendar aCalendar = aComplex.getTimeTable();
		for (Availability av : aCalendar.getAvailabilities()) {

			// Para fecha_dia_inicio
			String dayName = day2formName(av.getDay().toString(), "inicio");

			DateTime dt = av.getSchedule().getStartTime();
			String timeString = String.format("%02d:%02d", dt.getHourOfDay(),
					dt.getMinuteOfHour());

			data.add(new Pair<String, String>(dayName, timeString));

			// Para fecha_dia_fin
			dayName = day2formName(av.getDay().toString(), "fin");

			dt = av.getSchedule().getEndTime();
			timeString = String.format("%02d:%02d", dt.getHourOfDay(), dt
					.getMinuteOfHour());

			data.add(new Pair<String, String>(dayName, timeString));

		}

		aux = aComplex.getPlace().getCountry();
		if (aux != null)
			data.add(new Pair<String, String>("country", aux));

		if (policy != null) {

			iaux = policy.getDepositLimit();
			if (iaux != null)
				data.add(new Pair<String, String>("depositLimit", String
						.valueOf(iaux)));

			iaux = policy.getBookingLimit();
			if (iaux != null)
				data.add(new Pair<String, String>("bookingLimit", String
						.valueOf(iaux)));
		}

		this.populate(data);
	}

	private static String day2formName(String day, String ending) {

		return days.get(day) + ending;
	}
}
