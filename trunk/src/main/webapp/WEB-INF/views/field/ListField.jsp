<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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

<form action="" method="get">
	<fieldset>
	<legend>Búsqueda de canchas</legend>
	<label for="search">Nombre: </label>
	<input id="fieldName" type="text" name="search" value="<c:out value="${param.search}" />"/>
	<input type="submit" value="Buscar" class="submit-go" />
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
					<td><strong>Lugar</strong></td>
					<td><strong>Descipción</strong></td>
					<td><strong>Techada</strong></td>
					<td><strong>Tipo de piso</strong></td>
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
							<td><c:out value="${field.complex.place}" /></td>
							<td><c:out value="${field.description}" /></td>
							<c:if test="${(field.hasRoof)}">
								<td><c:out value="Sí" /></td>
							</c:if>
							<c:if test="${!(field.hasRoof)}">
								<td><c:out value="No" /></td>
							</c:if>
							
							<td><c:out value="${field.floor}" /></td>
							
							<td>
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