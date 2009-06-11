<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp"
	flush="true">
	<jsp:param name="title" value="Página Inicial" />
</jsp:include>

<h1>Página Inicial</h1>

<c:if test="${(param.update != null)}">

	<c:choose>
	  <c:when test="${param.update}">
	    <c:set var="profileMsg" scope="page" value="Perfil actualizado exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="profileMsg" scope="page" value="Error al actualizar el perfil"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Información:</strong></span>
		<span class="block">* <c:out value="${profileMsg}" /></span>
	</div>
</c:if>

<h3>¡Hola <c:out value="${user.username}" />! Bienvenido a la canchita. Aquí podrá realizar reservas para sus canchas preferidas.</h3>

<form action="<c:out value="${baseURI}" />/ListComplex" method="get">
	<fieldset>
	<legend>Búsqueda de complejos</legend>
	<label for="search">Nombre: </label>
	<input id="complexName" type="text" name="search"/>
	<input type="submit" value="Buscar" class="submit-go" />
	</fieldset>

</form>


<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />