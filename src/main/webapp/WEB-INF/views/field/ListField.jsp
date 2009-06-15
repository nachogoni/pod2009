<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link HREF=<c:out value="${baseURI}" escapeXml="false" />/LastFields TITLE="RSS" TYPE="application/rss+xml" rel="alternate">

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
	    <c:set var="deleteMsg" scope="page" value="La cancha fue borrada exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="deleteMsg" scope="page" value="Error al borrar la cancha"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Información:</strong></span>
		<span class="block">* <c:out value="${deleteMsg}" /></span>
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

<form action="" method="get">
	<fieldset>
	<legend>Búsqueda de canchas</legend>
	<label for="name">Nombre: </label>
	<input id="fieldName" type="text" name="name" value="<c:out value="${param.name}" />"/>
	<label for="description">Descripción: </label>
	<input id="fieldDescription" type="text" name="description" value="<c:out value="${param.description}" />"/>
	<label for="maxPrice">Precio máximo: </label>
	<input id="fieldMaxPrice" type="text" name="maxPrice" value="<c:out value="${param.maxPrice}" />"/>
	<label for="numberOfPlayers">Cantidad de jugadores: </label>
    <input id="fieldNumberOfPlayers" type="text" name="numberOfPlayers" value="<c:out value="${param.numberOfPlayers}" />"/>
    <br /><br />
	<label for="hasRoof">Techada: </label>
	Sí
    <input id="hasRoofYes" type="radio" name="hasRoof" value="yes"/>
	No
    <input id="hasRoofNo" type="radio" name="hasRoof" value="no"/>
    <br /><br />
    <label for="floorType">Tipo de piso: </label>
	Césped sintético
    <input id="floorTypeGrass" type="radio" name="floorType" value="1"/>
	Césped
    <input id="floorTypeArtificial" type="radio" name="floorType" value="3"/>
	Concreto
    <input id="floorTypeConcrete" type="radio" name="floorType" value="2"/>
	<input type="submit" name="search" value="Buscar" class="submit-go" />
	</fieldset>
</form>

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
<c:otherwise>
	<c:choose>
	<c:when test="${(fields != null)}">
	
	<%-- TODO ver como hacerque anden los function tags --%>
	
		<c:choose>
		<c:when test="${ fieldsLength != 0 }">
			<table class="fieldsTable" border="1">
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
								</c:if>
								<c:if test="${user.isAuthenticated}">
									<form action="<c:out value="${baseURI}" />/field/ListFieldExpirationPolicy" method="get">
									<input type="hidden" name="id" value="<c:out value="${field.id}"/>" />
									<input type="submit" name="expirationPolicy" value="Ver expiración" />
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
</c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />
