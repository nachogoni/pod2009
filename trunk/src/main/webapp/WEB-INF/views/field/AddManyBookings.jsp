<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp" flush="true">
	<jsp:param name="title" value="Realizar reserva" />
</jsp:include>

<h1>Realizar la reserva de la cancha</h1>

<c:if test="${(success != null)}">

	<div class="ui-state-highlight ui-corner-all info"> 
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Información:</strong></span>
		<c:choose>
			<c:when test="${ fieldsLength != 0 }">
				<c:forEach items="${bookings}" var="booking">
					<span class="block bookMsg">* Reserva agendada </span>
					<span class="block">* Desde: <c:out value="${booking.schedule.startTime.dayOfMonth}"/>/<c:out value="${booking.schedule.startTime.monthOfYear}"/> <c:out value="${booking.schedule.startTime.hourOfDay}"/>hs </span>
					<span class="block">* Hasta: <c:out value="${booking.schedule.endTime.dayOfMonth}"/>/<c:out value="${booking.schedule.endTime.monthOfYear}"/> <c:out value="${booking.schedule.endTime.hourOfDay}"/>hs </span>
					<span class="block">* Precio: $<c:out value="${booking.cost}"/> </span>
				</c:forEach>
			</c:when>
			<c:otherwise>
					<span class="block">* No se agendaron reservas</span>
			</c:otherwise>
		</c:choose>
	</div>
</c:if>

<c:if test="${(errorManager != null)}">
<div class="ui-state-error ui-corner-all error"> 

<span class="ui-icon ui-icon-alert errorIcon"></span>
<span>
<strong>Alerta:</strong>
</span>
		<c:forEach items="${errorManager.errors}" var="error">
			<span class="block">* <c:out value="${error}"/></span>
		</c:forEach>
</div>
</c:if>

<c:if test="${(field != null)}">
<div class="fieldInfo">
<div> Cancha: <c:out value="${field.name}"/> </div>
<div> Jugadores: <c:out value="${field.numberOfPlayers}"/> </div>
<div> Precio: $<c:out value="${field.price}"/> </div>
</div>


</c:if>

<form action="" method="post">
	<fieldset>
	<legend>Por favor, seleccione en intervalo de fechas donde desea reservar</legend>
	<div>
	<span><strong>IMPORTANTE:</strong> De estar algún día ocupado no se reservará para ese día</span>
	<label for="from">Desde: </label>
	<input id="datepicker1" type="text" maxlength="200" name="from" size="10"/>
	</div>
	<div>
	<label for="to">Hasta: </label>
	<input id="datepicker2" type="text" maxlength="200" name="to" size="10"/>
	</div>
	</fieldset>

	<div id="whenError" class="ui-state-error ui-corner-all hidden error"> 
	
		<span class="ui-icon ui-icon-alert errorIcon"></span>
		<span><strong>Alerta:</strong></span>
		<span class="block">* No hay horarios disponibles para este día</span>
	</div>

	<div id="whenJSONError" class="ui-state-error ui-corner-all hidden error"> 
	
		<span class="ui-icon ui-icon-alert errorIcon"></span>
		<span><strong>Alerta:</strong></span>
		<span id="whenJSONErrorMsg" class="block"></span>
	</div>


	
	<fieldset id="whenForm" >
	<legend>Ahora, seleccione el horario de entre los disponibles</legend>
	<select id="when" name="when">
		<option value="00:00 01:00">00:00 - 01:00</option>
		<option value="01:00 02:00">01:00 - 02:00</option>
		<option value="02:00 03:00">02:00 - 03:00</option>
		<option value="03:00 04:00">03:00 - 04:00</option>
		<option value="04:00 05:00">04:00 - 05:00</option>
		<option value="05:00 06:00">05:00 - 06:00</option>
		<option value="06:00 07:00">06:00 - 07:00</option>
		<option value="07:00 08:00">07:00 - 08:00</option>
		<option value="08:00 09:00">08:00 - 09:00</option>
		<option value="09:00 10:00">09:00 - 10:00</option>
		<option value="10:00 11:00">10:00 - 11:00</option>
		<option value="11:00 12:00">11:00 - 12:00</option>
		<option value="12:00 13:00">12:00 - 13:00</option>
		<option value="13:00 14:00">13:00 - 14:00</option>
		<option value="14:00 15:00">14:00 - 15:00</option>
		<option value="15:00 16:00">15:00 - 16:00</option>
		<option value="16:00 17:00">16:00 - 17:00</option>
		<option value="17:00 18:00">17:00 - 18:00</option>
		<option value="18:00 19:00">18:00 - 19:00</option>
		<option value="19:00 20:00">19:00 - 20:00</option>
		<option value="20:00 21:00">20:00 - 21:00</option>
		<option value="21:00 22:00">21:00 - 22:00</option>
		<option value="22:00 23:00">22:00 - 23:00</option>
		<option value="23:00 00:00">23:00 - 00:00</option>
		
		
	</select>
	</fieldset>
	
	<c:if test="${(param.id != null)}">
		<input id="id" type="hidden" name="id" value="<c:out value="${param.id}"/>"/>
	</c:if>
	
	<input type="hidden" name="check" value="true"/>

	<input type="submit" value="Agregar" class="submit-go" />
</form>



<jsp:include page="/WEB-INF/views/general/js.jsp"
	flush="true" />
<script src="<c:out value="${baseURI}" escapeXml="false" />/js/util/calendar.js" type="text/javascript"/></script>
<script src="<c:out value="${baseURI}" escapeXml="false" />/js/field/bookmany/init.js" type="text/javascript"/></script>

<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />