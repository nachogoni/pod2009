<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp"
	flush="true">
	<jsp:param name="title" value="Listado de Complejos" />
	<jsp:param name="header" value="admin" />
</jsp:include>

<jsp:include page="/WEB-INF/views/general/js.jsp"
	flush="true" />
<script src="<c:out value="${baseURI}" escapeXml="false"/>/js/util/delete.js" type="text/javascript"/></script>

<h1>Listado de Complejos</h1>

<%-- TODO generalizar esto --%>

<c:if test="${(param.add != null)}">

	<c:choose>
	  <c:when test="${param.add}">
	    <c:set var="addMsg" scope="page" value="El complejo fue agregado exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="addMsg" scope="page" value="Error al agregar el complejo"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Informaci�n:</strong></span>
		<span class="block">* <c:out value="${addMsg}" /></span>
	</div>
</c:if>

<c:if test="${(param.addPolicy != null)}">

	<c:choose>
	  <c:when test="${param.addPolicy}">
	    <c:set var="addMsg" scope="page" value="La pol�tica de expiraci�n ha sido agregada exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="addMsg" scope="page" value="Error al agregar la pol�tica"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Informaci�n:</strong></span>
		<span class="block">* <c:out value="${addMsg}" /></span>
	</div>
</c:if>

<c:if test="${(param.modify != null)}">

	<c:choose>
	  <c:when test="${param.modify}">
	    <c:set var="modifyMsg" scope="page" value="El complejo fue modificado exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="modifyMsg" scope="page" value="Error al modificar el complejo"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Informaci�n:</strong></span>
		<span class="block">* <c:out value="${modifyMsg}" /></span>
	</div>
</c:if>

<c:if test="${(param.modifyPolicy != null)}">

	<c:choose>
	  <c:when test="${param.modifyPolicy}">
	    <c:set var="modifyMsg" scope="page" value="La pol�tica fue modificada exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="modifyMsg" scope="page" value="Error al modificar la pol�tica"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Informaci�n:</strong></span>
		<span class="block">* <c:out value="${modifyMsg}" /></span>
	</div>
</c:if>


<c:if test="${(param.delete != null)}">

	<c:choose>
	  <c:when test="${param.delete}">
	    <c:set var="deleteMsg" scope="page" value="El complejo fue eliminado exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="deleteMsg" scope="page" value="Error al eliminar el complejo"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Informaci�n:</strong></span>
		<span class="block">* <c:out value="${deleteMsg}" /></span>
	</div>
</c:if>

<c:if test="${(param.hasBookings != null && param.hasBookings)}">

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Informaci�n:</strong></span>
		<span class="block">* No se puede eliminar un complejo que tiene reservas activas</span>
	</div>
</c:if>

<c:if test="${(param.deletePolicy != null)}">

	<c:choose>
	  <c:when test="${param.deletePolicy}">
	    <c:set var="deleteMsg" scope="page" value="La pol�tica fue borrada exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="deleteMsg" scope="page" value="Error al borrar la pol�tica"/>
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
	<input id="complexName" type="text" maxlength="200" name="search" value="<c:out value="${param.search}" />"/>
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
	<c:when test="${(complexes != null)}">
	
	<%-- TODO ver como hacerque anden los function tags --%>
	
		<c:choose>
		<c:when test="${ complexesLength != 0 }">
			<table class="complexesTable" border="1">
				<tr>
					<td><strong>Nombre</strong></td>
					<td><strong>Lugar</strong></td>
					<td><strong>Descripci�n</strong></td>
					<td>
						<c:if test="${user.isAdmin}">
							<form action="<c:out value="${baseURI}" />/AddComplex" method="get">
								<input type="submit" name="addComplex" value="Agregar Nuevo" />
							</form>
						</c:if>
					</td>
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
								<c:if test="${user.isAdmin}">
									<form action="<c:out value="${baseURI}" />/DeleteComplex" method="post">
									<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
									<input type="hidden" name="id" value="<c:out value="${complex.id}"/>" />
									<input type="submit" class="delete" name="delete" value="Eliminar" />
									</form>
									<form action="<c:out value="${baseURI}" />/ModifyComplex" method="get">
									<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
									<input type="hidden" name="id" value="<c:out value="${complex.id}"/>" />
									<input type="submit" name="modify" value="Modificar" />
									</form>
									<form action="<c:out value="${baseURI}" />/booking/listcomplex" method="get">
									<!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
									<input type="hidden" name="id" value="<c:out value="${complex.id}"/>" />
									<input type="submit" name="list" value="Ver Reservas" />
									</form>
								</c:if>
								<c:if test="${user.isAuthenticated}">
									<form action="<c:out value="${baseURI}" />/ListExpirationPolicy" method="get">
									<input type="hidden" name="id" value="<c:out value="${complex.id}"/>" />
									<input type="submit" name="expirationPolicy" value="Ver expiraci�n" />
									</form>
								</c:if>
								<form action="<c:out value="${baseURI}" />/DetailedViewComplex" method="get">
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
					<span class="block">* No se encontraron complejos</span>
				</div>
			
				<c:if test="${user.isAdmin}">
					<div class="submit-go">
						<a href="<c:out value="${baseURI}" />/AddComplex" >Agregar</a>
					</div>
				</c:if>
			
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