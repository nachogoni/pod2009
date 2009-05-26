<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp"
	flush="true">
	<jsp:param name="title" value="Detalle de Complejo" />
	<jsp:param name="header" value="admin" />
</jsp:include>


<jsp:include page="/WEB-INF/views/general/js.jsp"
	flush="true" />
<script src="/tp-pod/js/util/delete.js" type="text/javascript"/></script>

<h1>Vista Detallada</h1>

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

<table>
	<tr>
		<td>
			<form action="/tp-pod/DeleteComplex" method="post">
			<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
			<input type="hidden" name="id" value="<c:out value="${complex.id}"/>" />
			<input type="submit" class="delete" name="delete" value="Eliminar" />
			</form>
		</td>
		<td>
			<form action="/tp-pod/ModifyComplex" method="get">
			<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
			<input type="hidden" name="id" value="<c:out value="${complex.id}"/>" />
			<input type="submit" name="modify" value="Modificar" />
			</form>
		</td>
		<td>
			<form action="/tp-pod/field/add" method="get">
			<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
			<input type="hidden" name="id" value="<c:out value="${complex.id}"/>" />
			<input type="submit" name="add" value="Agregar Canchas" />
			</form>
		</td>
	</tr>
</table>

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
					<td><strong>Canchas:</strong></td>				
					<td>
									
					<c:choose>
						
						<c:when test="${fields != null}">
							
							<c:forEach items="${fields}" var="field" varStatus="rowCounter">
						        <!--
						        <c:choose>
						          <c:when test="${rowCounter.count % 2 == 0}">
						            <c:set var="rowStyle" scope="page" value="odd"/>
						          </c:when>
						          <c:otherwise>
						            <c:set var="rowStyle" scope="page" value=""/>
						          </c:otherwise>
						        </c:choose>
						        -->
								
								<!--  <tr class="<c:out value="${rowStyle}" />"> -->
								<tr>
									<td><c:out value="${field.name}" /></td>
									<td><a href=<c:out value="field/book?id=${field.id}" />>Reservar</a></td>
								</tr>
								
							</c:forEach>
							
						</c:when>
						
						<c:otherwise>
							Este complejo todavia no tiene canchas
						</c:otherwise>
						
					</c:choose>
					
					</td>
				</tr>
				<tr>

					<td><strong>Sistema de Puntos</strong></td>
					<td>
					<table>
						<c:choose>
						<c:when test="${complex.scoreSystem != null}">
							
							<tr>
							<td><strong>Reserva: </strong></td>
							<td><c:out value="${complex.scoreSystem.booking}" /></td>
							</tr>
							
							<tr>
							<td><strong>Deposito: </strong></td>
							<td><c:out value="${complex.scoreSystem.deposit}" /></td>
							</tr>
							
							<tr>
							<td><strong>Pago: </strong></td>
							<td><c:out value="${complex.scoreSystem.pay}" /></td>
							</tr>
							
							<tr>
							<td><strong>Cancelar Reserva: </strong></td>
							<td>-<c:out value="${complex.scoreSystem.downBooking}" /></td>
							</tr>
							
							<tr>
							<td><strong>Cancelar Deposito: </strong></td>
							<td>-<c:out value="${complex.scoreSystem.downDeposit}" /></td>
							</tr>				
							
						</c:when>
						<c:otherwise>
							Este complejo todavia no tiene un sistema de puntos asignado.
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