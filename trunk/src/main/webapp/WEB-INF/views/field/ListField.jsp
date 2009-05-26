<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp"
	flush="true">
	<jsp:param name="title" value="Listado de Canchas" />
	<jsp:param name="header" value="admin" />
</jsp:include>

<h1>Listado de Canchas</h1>
<form action="/tp-pod/ListField" method="get">
	<fieldset>
	<legend>Búsqueda de canchas</legend>
	<label for="search">Nombre: </label>
	<input id="fieldName" type="text" name="search" value="<c:out value="${param.name}" />"/>
	</fieldset>

	<input type="submit" value="Buscar" class="submit-go" />
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
					<td><a href="/tp-pod/AddField">Agregar Nuevo</a></td>
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
							<td><c:out value="${field.complex.description}" /></td>
							
							<td>
								<form action="/tp-pod/DeleteField" method="post">
								<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
								<input type="hidden" name="id" value="<c:out value="${field.id}"/>" />
								<input type="submit" name="delete" value="Eliminar" />
								</form>
								<form action="/tp-pod/ModifyField" method="get">
								<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
								<input type="hidden" name="id" value="<c:out value="${field.id}"/>" />
								<input type="submit" name="modify" value="Modificar" />
								</form>
								<form action="/tp-pod/DetailedViewField" method="get">
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
			
				<div class="submit-go">
					<a href="/tp-pod/AddField" >Agregar</a>
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