<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link HREF="<c:out value="${baseURI}" escapeXml="false" />/LastFields" TITLE="RSS" TYPE="application/rss+xml" rel="alternate">

<jsp:include page="/WEB-INF/views/general/header/header.jsp"
	flush="true">
	<jsp:param name="title" value="Listado de Canchas" />
</jsp:include>


<jsp:include page="/WEB-INF/views/general/js.jsp"
	flush="true" />
<script src="<c:out value="${baseURI}" escapeXml="false" />/js/util/delete.js" type="text/javascript"/></script>

<h1>Listado de Canchas</h1>

<%-- TODO generalizar esto --%>

<c:if test="${(param.add != null)}">

	<c:choose>
	  <c:when test="${param.add}">
	    <c:set var="addMsg" scope="page" value="La cancha fue agregada exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="addMsg" scope="page" value="Error al agregar la cancha"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Información:</strong></span>
		<span class="block">* <c:out value="${addMsg}" /></span>
	</div>
</c:if>

<c:if test="${(param.addPolicy != null)}">

	<c:choose>
	  <c:when test="${param.addPolicy}">
	    <c:set var="addMsg" scope="page" value="La política de expiración ha sido agregada exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="addMsg" scope="page" value="Error al agregar la política"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Información:</strong></span>
		<span class="block">* <c:out value="${addMsg}" /></span>
	</div>
</c:if>

<c:if test="${(param.modify != null)}">

	<c:choose>
	  <c:when test="${param.modify}">
	    <c:set var="modifyMsg" scope="page" value="La cancha fue modificada exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="modifyMsg" scope="page" value="Error al modificar la cancha"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Información:</strong></span>
		<span class="block">* <c:out value="${modifyMsg}" /></span>
	</div>
</c:if>

<c:if test="${(param.modifyPolicy != null)}">

	<c:choose>
	  <c:when test="${param.modifyPolicy}">
	    <c:set var="modifyMsg" scope="page" value="La política fue modificada exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="modifyMsg" scope="page" value="Error al modificar la política"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Información:</strong></span>
		<span class="block">* <c:out value="${modifyMsg}" /></span>
	</div>
</c:if>

<c:if test="${(param.delete != null)}">

	<c:choose>
	  <c:when test="${param.delete}">
	    <c:set var="deleteMsg" scope="page" value="La cancha fue eliminada exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="deleteMsg" scope="page" value="Error al eliminar la cancha"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Información:</strong></span>
		<span class="block">* <c:out value="${deleteMsg}" /></span>
	</div>
</c:if>

<c:if test="${(param.hasBookings != null && param.hasBookings)}">

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Información:</strong></span>
		<span class="block">* No se puede eliminar una cancha que tiene reservas activas</span>
	</div>
</c:if>


<c:if test="${(param.deletePolicy != null)}">

	<c:choose>
	  <c:when test="${param.deletePolicy}">
	    <c:set var="deleteMsg" scope="page" value="La política fue borrada exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="deleteMsg" scope="page" value="Error al borrar la política"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Información:</strong></span>
		<span class="block">* <c:out value="${deleteMsg}" /></span>
	</div>
</c:if>


<c:choose>
<c:when  test="${(searchError != null)}">
<div class="ui-state-error ui-corner-all error"> 

<span class="ui-icon ui-icon-alert errorIcon"></span>
<span>
<strong>Alerta:</strong>
</span>
		<c:forEach items="${searchError.errors}" var="error">
			<span class="block">* <c:out value="${error}"/></span>
		</c:forEach>
</div>
</c:when>
</c:choose>

<!-- Mostra el error y el form-->
<c:out value="${formulario}" escapeXml="false" />

	<c:choose>
	<c:when test="${(fields != null)}">
	
	<%-- TODO ver como hacer que anden los function tags --%>
	
	<c:choose>
	<c:when test="${ fieldsLength != 0 }">
		<table class="fieldsTable" border="1">
			<tr>
                <td><strong><a href="<c:out value="${baseURI}" escapeXml="false"/>/field/list?sortName=<c:out value="${sortNameTypeR}" escapeXml="false"/>">Nombre</a></strong></td>
				<td><strong><a href="<c:out value="${baseURI}" escapeXml="false"/>/field/list?sortComplex=<c:out value="${sortComplexTypeR}" escapeXml="false"/>">Complejo</a></strong></td>
				<td><strong><a href="<c:out value="${baseURI}" escapeXml="false"/>/field/list?sortDescription=<c:out value="${sortDescriptionTypeR}" escapeXml="false"/>">Descripción</a></strong></td>
                <td><strong><a href="<c:out value="${baseURI}" escapeXml="false"/>/field/list?sortPlayers=<c:out value="${sortPlayersTypeR}" escapeXml="false"/>">Cantidad de Jugadores</a></strong></td>
				<td><strong><a href="<c:out value="${baseURI}" escapeXml="false"/>/field/list?sortRoof=<c:out value="${sortRoofTypeR}" escapeXml="false"/>">Techada</a></strong></td>
				<td><strong><a href="<c:out value="${baseURI}" escapeXml="false"/>/field/list?sortFloor=<c:out value="${sortFloorTypeR}" escapeXml="false"/>">Tipo de piso</a></strong></td>
                <td><strong><a href="<c:out value="${baseURI}" escapeXml="false"/>/field/list?sortPrice=<c:out value="${sortPriceTypeR}" escapeXml="false"/>">Precio</a></strong></td>
                <td><strong><a href="<c:out value="${baseURI}" escapeXml="false"/>/field/list?sortMaintenance=<c:out value="${sortMaintenanceTypeR}" escapeXml="false"/>">En Mantenimiento</a></strong></td>
				<td>
					<c:if test="${user.isAdmin}">
						<form action="<c:out value="${baseURI}" />/field/add" method="get">
							<input type="submit" class="" name="addfields" value="Agregar Nueva" />
						</form>
					</c:if>
				</td>
			</tr>
			<c:forEach items="${fields}" var="field" varStatus="rowCounter">
					
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
							<form action="<c:out value="${baseURI}" />/field/detailedview" method="get">
							<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
							<input type="hidden" name="id" value="<c:out value="${field.id}"/>" />
							<input type="submit" name="viewDetails" value="Detalles" />
							</form>
						</td>
					</tr>
					
				</c:forEach>
		</table>
		</c:when>
		<c:otherwise>
			<div class="ui-state-highlight ui-corner-all info"> 
			
				<span class="ui-icon ui-icon-info infoIcon"></span>
				<span><strong>Información:</strong></span>
				<span class="block">* No se encontraron canchas</span>
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


<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />
