<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp"
	flush="true">
	<jsp:param name="title" value="Listado de Reservas" />
</jsp:include>


<jsp:include page="/WEB-INF/views/general/js.jsp"
	flush="true" />

<h1>Listado de Reservas</h1>

<c:choose>
<c:when  test="${(errorManager != null)}">
<div class="ui-state-error ui-corner-all error"> 

<span class="ui-icon ui-icon-alert errorIcon"></span>
<span>
<strong>Alerta:</strong>
</span>
		<c:forEach items="${errorManager.errors}" var="error">
			<span class="block">* <c:out value="${error}"/></span>
		</c:forEach>
</div>
</c:when>
<c:otherwise>

<c:if test="${(param.pay != null)}">



	<c:choose>
	  <c:when test="${param.pay}">
	    <c:set var="payMsg" scope="page" value="El pago fue cargado exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="payMsg" scope="page" value="Error al cargar el pago"/>
	  </c:otherwise>
	</c:choose>

	<c:choose>
	  <c:when test="${param.exceeded != null && param.exceeded}">
	    <c:set var="exceededMsg" scope="page" value=". Sin embargo, se ingresó una cantidad mayor al total de la reserva, por lo que se debitó únicamente la cantidad necesaria para completar el pago de la misma"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="exceededMsg" scope="page" value=""/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Información:</strong></span>
		<span class="block">* <c:out value="${payMsg}" /><c:out value="${exceededMsg}" /></span>
	</div>
</c:if>

<c:if test="${(param.fullpay != null)}">

	<c:choose>
	  <c:when test="${param.fullpay}">
	    <c:set var="fullPayMsg" scope="page" value="El pago completo fue cargado exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="fullPayMsg" scope="page" value="Error al cargar el pago completo"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Información:</strong></span>
		<span class="block">* <c:out value="${fullPayMsg}" /></span>
	</div>
</c:if>

	<c:choose>
	<c:when test="${(bookings != null)}">
	
	<%-- TODO ver como hacerque anden los function tags --%>
	
		<c:choose>
		<c:when test="${ bookingsLength != 0 }">
			<table class="fieldsTable" border="1">
				<tr>
                    <td><strong>Usuario</strong></td>
                    <c:if test="${showField}">
                    	<td><strong>Cancha</strong></td>
                    </c:if>
                    <c:if test="${showComplex}">
                    	<td><strong>Complejo</strong></td>
                    </c:if>
					<td><strong>Estado</strong></td>
					<td><strong>Costo</strong></td>
                    <td><strong>Pagado</strong></td>
					<td></td>
				</tr>
				<c:forEach items="${bookings}" var="booking" varStatus="rowCounter">
						
				        <c:choose>
				          <c:when test="${rowCounter.count % 2 == 0}">
				            <c:set var="rowStyle" scope="page" value="odd"/>
				          </c:when>
				          <c:otherwise>
				            <c:set var="rowStyle" scope="page" value=""/>
				          </c:otherwise>
				        </c:choose>
						
						<tr class="<c:out value="${rowStyle}" />">
							<td><c:out value="${booking.owner.username}" /></td>
							 <c:if test="${showField}">
								<td><a href="<c:out value="${baseURI}" escapeXml="false"/>/field/detailedview?id=<c:out value="${booking.item.id}" />"><c:out value="${booking.item.name}" /></a></td>
							</c:if>
							<c:if test="${showComplex}">
								<td><a href="<c:out value="${baseURI}" escapeXml="false"/>/DetailedViewComplex?id=<c:out value="${booking.item.complex.id}" />"><c:out value="${booking.item.complex.name}" /></a></td> 
							</c:if>
							<td><c:out value="${booking.state}" /></td>
							<td><c:out value="${booking.cost}" /></td>
							<td><c:out value="${booking.paid}" /></td>
							
							<td>
						        <c:if test = "${booking.state != 'Pagada'}">     
									<form action="<c:out value="${baseURI}" />/booking/pay" method="get">
										<input type="hidden" name="id" value="<c:out value="${booking.id}"/>" />
										<input type="submit" name="pay" value="Cargar pago" />
									</form>
									<form action="<c:out value="${baseURI}" />/booking/fullpay" method="post">
										<input type="hidden" name="id" value="<c:out value="${booking.id}"/>" />
										<input type="submit" name="pay" value="Cargar pago completo" />
									</form>
								</c:if>
							</td>
						</tr>
						
					</c:forEach>
			</table>
			</c:when>
			<c:otherwise>
				<div class="ui-state-highlight ui-corner-all info"> 
				
					<span class="ui-icon ui-icon-info infoIcon"></span>
					<span><strong>Información:</strong></span>
					<span class="block">* No se encontraron reservas</span>
				</div>
			
			</c:otherwise>
			</c:choose>
	</c:when>
	<c:otherwise>
		<div class="ui-state-error ui-corner-all error"> 
		
		<span class="ui-icon ui-icon-alert errorIcon"></span>
		<span>
		<strong>Alerta:</strong>
		</span>
			<span class="block">* Error en el servidor</span>
		</div>
	</c:otherwise>
	</c:choose>
</c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />
