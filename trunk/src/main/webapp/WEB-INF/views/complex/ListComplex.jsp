<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp"
	flush="true">
	<jsp:param name="title" value="Listado de Complejos" />
	<jsp:param name="header" value="admin" />
</jsp:include>

<jsp:include page="/WEB-INF/views/general/js.jsp"
	flush="true" />
<script src="/tp-pod/js/util/delete.js" type="text/javascript"/></script>

<h1>Listado de Complejos</h1>

<c:if test="${(param.delete != null)}">

	<c:choose>
	  <c:when test="${param.delete}">
	    <c:set var="deleteMsg" scope="page" value="El complejo fue borrado exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="deleteMsg" scope="page" value="Error al borrar el complejo"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Informaci�n:</strong></span>
		<span class="block">* <c:out value="${deleteMsg}" /></span>
	</div>
</c:if>

<form action="" method="get">
	<fieldset>
	<legend>B�squeda de complejos</legend>
	<label for="search">Nombre: </label>
	<input id="complexName" type="text" name="search" value="<c:out value="${param.search}" />"/>
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
	<c:when test="${(complexes != null)}">
	
	<%-- TODO ver como hacerque anden los function tags --%>
	
		<c:choose>
		<c:when test="${ complexesLength != 0 }">
			<table class="complexesTable" border="1">
				<tr>
					<td><strong>Nombre</strong></td>
					<td><strong>Lugar</strong></td>
					<td><strong>Descipci�n</strong></td>
					<td><a href="/tp-pod/AddComplex">Agregar Nuevo</a></td>
				</tr>
				<c:forEach items="${complexes}" var="complex" varStatus="rowCounter">
						
				        <c:choose>
				          <c:when test="${rowCounter.count % 2 == 0}">
				            <c:set var="rowStyle" scope="page" value="odd"/>
				          </c:when>
				          <c:otherwise>
				            <c:set var="rowStyle" scope="page" value=""/>
				          </c:otherwise>
				        </c:choose>
						
						<tr class="<c:out value="${rowStyle}" />">
							<td><c:out value="${complex.name}" /></td>
							<td><c:out value="${complex.place}" /></td>
							<td><c:out value="${complex.description}" /></td>
							
							<td>
								<form action="/tp-pod/DeleteComplex" method="post">
								<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
								<input type="hidden" name="id" value="<c:out value="${complex.id}"/>" />
								<input type="submit" class="delete" name="delete" value="Eliminar" />
								</form>
								<form action="/tp-pod/ModifyComplex" method="get">
								<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
								<input type="hidden" name="id" value="<c:out value="${complex.id}"/>" />
								<input type="submit" name="modify" value="Modificar" />
								</form>
								<form action="/tp-pod/DetailedViewComplex" method="get">
								<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
								<input type="hidden" name="id" value="<c:out value="${complex.id}"/>" />
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
					<span><strong>Informaci�n:</strong></span>
					<span class="block">* No se encontraron canchas</span>
				</div>
			
				<div class="submit-go">
					<a href="/tp-pod/AddComplex" >Agregar</a>
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