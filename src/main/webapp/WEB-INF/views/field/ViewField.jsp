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

<table>
	<tr>
		<td>
			<form action="<c:out value="${baseURI}" />/field/delete" method="post">
			<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
			<input type="hidden" name="id" value="<c:out value="${field.id}"/>" />
			<input type="submit" class="delete" name="delete" value="Eliminar" />
			</form>
		</td>
		<td><a href=<c:out value="<c:out value="${baseURI}" />/field/book?id=${field.id}" />>Reservar</a></td>
		<!-- 
		<td>
			<form action="<c:out value="${baseURI}" />/ModifyField" method="get">
			<input type="hidden" name="id" value="<c:out value="${field.id}"/>" />
			<input type="submit" name="modify" value="Modificar" />
			</form>
		</td>
		-->
	</tr>
</table>

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
					<td><c:out value="${field.name}" /></td>
				</tr>
				<tr>
					<td><strong>Descripción</strong></td>
					<td><c:out value="${field.description}" /></td>
				</tr>
				<tr>

				<td><strong>Sistema de Puntos</strong></td>
				<td>
				<table>
					<c:choose>
					<c:when test="${field.scoreSystem != null}">
						
						<tr>
						<td><strong>Reserva: </strong></td>
						<td><c:out value="${field.scoreSystem.booking}" /></td>
						</tr>
						
						<tr>
						<td><strong>Deposito: </strong></td>
						<td><c:out value="${field.scoreSystem.deposit}" /></td>
						</tr>
						
						<tr>
						<td><strong>Pago: </strong></td>
						<td><c:out value="${field.scoreSystem.pay}" /></td>
						</tr>
						
						<tr>
						<td><strong>Cancelar Reserva: </strong></td>
						<td>-<c:out value="${field.scoreSystem.downBooking}" /></td>
						</tr>
						
						<tr>
						<td><strong>Cancelar Deposito: </strong></td>
						<td>-<c:out value="${field.scoreSystem.downDeposit}" /></td>
						</tr>				
						
					</c:when>
					<c:otherwise>
						Esta cancha todavia no tiene un sistema de puntos asignado.
					</c:otherwise>
					</c:choose>
				</table>
				</td>
				
				</tr>
			</table>

</c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />