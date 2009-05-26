<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp"
	flush="true">
	<jsp:param name="title" value="Listado de Complejos" />
	<jsp:param name="header" value="admin" />
</jsp:include>

<h1>Vista Detallada</h1>

<table>
	<tr>
		<td>
			<form action="/tp-pod/DeleteComplex" method="post">
			<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
			<input type="hidden" name="id" value="<c:out value="${complex.id}"/>" />
			<input type="submit" name="delete" value="Eliminar" />
			</form>
		</td>
		<td>
			<form action="/tp-pod/ModifyComplex" method="post">
			<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
			<input type="hidden" name="id" value="<c:out value="${complex.id}"/>" />
			<input type="submit" name="modify" value="Modificar" />
			</form>
		</td>
		<td>
			<form action="/tp-pod/ModifyComplex" method="post">
			<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
			<input type="hidden" name="id" value="<c:out value="${complex.id}"/>" />
			<input type="submit" name="modify" value="Agregar Canchas" />
			</form>
		</td>
	</tr>
</table>

<c:choose>
<c:when  test="${(complex == null)}">
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
					<td><strong>Descipción</strong></td>
					<td><c:out value="${complex.description}" /></td>
				</tr>
				<tr>
					<td><strong>Canchas:</strong></td>				
					<td>
									
					<c:choose>
						
						<c:when test="${complex.fields != null}">
							
							<c:forEach items="${complex.fields}" var="field" varStatus="rowCounter">
						        
						        <c:choose>
						          <c:when test="${rowCounter.count % 2 == 0}">
						            <c:set var="rowStyle" scope="page" value="odd"/>
						          </c:when>
						          <c:otherwise>
						            <c:set var="rowStyle" scope="page" value=""/>
						          </c:otherwise>
						        </c:choose>
								
								<tr class="<c:out value="${rowStyle}" />">
									<td><c:out value="${field.name}" /></td>
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