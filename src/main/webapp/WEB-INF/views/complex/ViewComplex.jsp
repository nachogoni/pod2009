<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp"
	flush="true">
	<jsp:param name="title" value="Detalle de Complejo" />
</jsp:include>


<jsp:include page="/WEB-INF/views/general/js.jsp"
	flush="true" />
<script src="<c:out value="${baseURI}" escapeXml="false" />/js/util/delete.js" type="text/javascript"/></script>

<h1>Vista Detallada del Complejo</h1>

<c:if test="${(param.booking != null)}">

	<c:choose>
	  <c:when test="${param.booking}">
	    <c:set var="bookingMsg" scope="page" value="Reserva agendada exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="bookingMsg" scope="page" value="Error al agendar la reserva"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Información:</strong></span>
		<span class="block">* <c:out value="${bookingMsg}" /></span>
	</div>
</c:if>

<c:choose>
<c:when  test="${(complex == null)}">
<div class="ui-state-error ui-corner-all error"> 

<span class="ui-icon ui-icon-alert errorIcon"></span>
<span>
<strong>Alerta:</strong>
</span>
		<c:forEach items="${errorManager.errors}" var="error">
			<span class="block">* <c:out value="${error}" /></span>
		</c:forEach>
</div>
</c:when>
<c:otherwise>	

<c:if test="${user.isAdmin}">
	<table>
		<tr>
			<td>
				<form action="<c:out value="${baseURI}" />/DeleteComplex" method="post">
				<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
				<input type="hidden" name="id" value="<c:out value="${complex.id}"/>" />
				<input type="submit" class="delete" name="delete" value="Eliminar" />
				</form>
			</td>
			<td>
				<form action="<c:out value="${baseURI}" />/ModifyComplex" method="get">
				<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
				<input type="hidden" name="id" value="<c:out value="${complex.id}"/>" />
				<input type="submit" name="modify" value="Modificar" />
				</form>
			</td>
			<td>
				<form action="<c:out value="${baseURI}" />/field/add" method="get">
				<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
				<input type="hidden" name="id" value="<c:out value="${complex.id}"/>" />
				<input type="submit" name="add" value="Agregar Canchas" />
				</form>
			</td>
		</tr>
	</table>
</c:if>

	<%-- TODO ver como hacerque anden los function tags --%>
	
			<table class="complexesTable" border="1">
				<tr>
					<td><strong>Nombre</strong></td>
					<td><c:out value="${complex.name}" /></td>
				</tr>
				<tr>
					<td><strong>Lugar</strong></td>
					<td><c:out value="${complex.place}" /></td>
				</tr>
				<tr>
					<td><strong>Descripción</strong></td>
					<td><c:out value="${complex.description}" /></td>
				</tr>
				<tr>
					<td><strong>Horarios</strong></td>
					<td>	
					<c:choose>
						<c:when test="${complex != null}">
							<c:forEach items="${complex.prettyTimetable}" var="timetable" varStatus="rowCounter">
								<strong><c:out value="${timetable.day}" />:</strong>
								<c:out value="${timetable.schedule.startTime.hourOfDay}" />:
						        <c:choose>
						          <c:when test="${timetable.schedule.startTime.minuteOfHour == 0}">
						          00
						          </c:when>
						          <c:otherwise>
						          <c:out value="${timetable.schedule.startTime.minuteOfHour}" />
						          </c:otherwise>
						        </c:choose>
								a
								<c:out value="${timetable.schedule.endTime.hourOfDay}" />:
					        	<c:choose>
						          <c:when test="${timetable.schedule.endTime.minuteOfHour == 0}">
						          00
						          </c:when>
						          <c:otherwise>
						          <c:out value="${timetable.schedule.endTime.minuteOfHour}" />
						          </c:otherwise>
						        </c:choose>
								<br />
							</c:forEach>
						</c:when>
						<c:otherwise>
							El complejo no tiene horarios cargados.
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
				</table>
				
				<c:choose>
					<c:when test="${fields != null}">
						<h2>Canchas del complejo</h2>
						<table class="complexesTable" border="1">
							<tr>
								<td><b>Nombre</b></td>
								<td><b>Descripción</b></td>
								<td><b>Cantidad de Jugadores</b></td>
								<td><b>Techada</b></td>
								<td><b>Tipo de piso</b></td>
								<td><b>Precio</b></td>
								<td><b>En Mantenimiento</b></td>
								<td> </td>
							</tr>
							<c:forEach items="${fields}" var="field" varStatus="rowCounter">
							<tr>
								<td><c:out value="${field.name}" /></td>
								<td><c:out value="${field.description}" /></td>
								<td><c:out value="${field.numberOfPlayers}" /></td>
								<c:if test="${(field.hasRoof)}">
									<td><c:out value="Sí" /></td>
								</c:if>
								<c:if test="${!(field.hasRoof)}">
									<td><c:out value="No" /></td>
								</c:if>
								<td><c:out value="${field.floor}" /></td>
								<td><c:out value="${field.price}" /></td>
								<c:if test="${(field.under_maintenance)}">
                                	<td><c:out value="Sí" /></td>
                            	</c:if>
                            	<c:if test="${!(field.under_maintenance)}">
                                	<td><c:out value="No" /></td>
                            	</c:if>
								<td>
									<c:if test="${user.isAuthenticated && !user.isAdmin}">
										<a href=<c:out value="field/book?id=${field.id}" />>Reservar</a>
										<br />
									</c:if>
									<a href=<c:out value="field/detailedview?id=${field.id }" />>Detalles</a>
								</td>
							</tr>
							</c:forEach>
						</table>
					</c:when>
						
					<c:otherwise>
						<h3>Este complejo todavia no tiene canchas</h3>
					</c:otherwise>
				</c:choose>
</c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />
