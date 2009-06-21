<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp"
	flush="true">
	<jsp:param name="title" value="Detalle de Cancha" />
</jsp:include>


<jsp:include page="/WEB-INF/views/general/js.jsp"
	flush="true" />
<script src="<c:out value="${baseURI}" escapeXml="false" />/js/util/delete.js" type="text/javascript"/></script>

<h1>Vista Detallada de la Cancha</h1>

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
<c:when  test="${(field == null)}">
<div class="ui-state-error ui-corner-all error"> 

<span class="ui-icon ui-icon-alert errorIcon"></span>
<span>
<strong>Alerta:</strong>
</span>
		<c:forEach items="${searchError.errors}" var="error">
			<span class="block">* Informacion no disponible</span>
		</c:forEach>
</div>
</c:when>
<c:otherwise>	
	<%-- TODO ver como hacerque anden los function tags --%>
	
			<table class="fieldTable" border="1">
				<tr>
                    <td><strong>Nombre</strong></td>
                    <td><strong>Complejo</strong></td>
					<td><strong>Descripción</strong></td>
					<td><strong>Cantidad de jugadores</strong></td>
					<td><strong>Techada</strong></td>
					<td><strong>Tipo de piso</strong></td>
					<td><strong>Precio</strong></td>
					<td><strong>En Mantenimiento</strong></td>
					<td></td>
				</tr>
				<tr>
					<td><c:out value="${field.name}" /></td>
							<td><a href="<c:out value="${baseURI}" escapeXml="false"/>/DetailedViewComplex?id=<c:out value="${field.complex.id}" />"><c:out value="${field.complex.name}" /></a></td> 
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
								<c:if test="${user.isAdmin}">
									<form action="<c:out value="${baseURI}" />/field/delete" method="post">
									<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
									<input type="hidden" name="id" value="<c:out value="${field.id}"/>" />
									<input type="submit" class="delete" name="delete" value="Eliminar" />
									</form>
									<form action="<c:out value="${baseURI}" />/field/modify" method="get">
									<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
									<input type="hidden" name="id" value="<c:out value="${field.id}"/>" />
									<input type="submit" name="modify" value="Modificar" />
									</form>
									<form action="<c:out value="${baseURI}" />/booking/listfield" method="get">
									<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
									<input type="hidden" name="id" value="<c:out value="${field.id}"/>" />
									<input type="submit" name="list" value="Ver Reservas" />
									</form>
								</c:if>
								<c:if test="${user.isAuthenticated}">
									<form action="<c:out value="${baseURI}" />/field/ListFieldExpirationPolicy" method="get">
									<input type="hidden" name="id" value="<c:out value="${field.id}"/>" />
									<input type="submit" name="expirationPolicy" value="Ver expiración" />
									</form>
								</c:if>
                                <c:if test="${user.isAuthenticated && !user.isAdmin}">
                                    <form action="<c:out value="${baseURI}" />/field/book" method="get">
                                    <input type="hidden" name="id" value="<c:out value="${field.id}"/>" />
                                    <input type="submit" name="id" value="Reservar" />
                                    </form>
                                </c:if>
							</td>
				</tr>
			</table>

</c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />