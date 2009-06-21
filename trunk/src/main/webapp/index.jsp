
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp"
	flush="true">
	<jsp:param name="title" value="micanchita.com" />
	<jsp:param name="header" value="guest" />
</jsp:include>

<h1>Est�s entrando en la canchita...</h1>

<c:if test="${(param.register != null)}">

	<c:choose>
	  <c:when test="${param.register}">
	    <c:set var="registerMsg" scope="page" value="La registraci�n se complet� exitosamente, consult� tu correo electr�nico para confirmarlo"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="registerMsg" scope="page" value="Error al realizar la registraci�n"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Informaci�n:</strong></span>
		<span class="block">* <c:out value="${registerMsg}" /></span>
	</div>
</c:if>

<h3>�Busc� un complejo amigo y reserv� tu cancha en segundos!</h3>

<img alt="micanchita.com" class="image" src="img/fieldPortada.jpg" />
<form action="<c:out value="${baseURI}" />/ListComplex" method="get">
	<fieldset>
	<legend>B�squeda de complejos</legend>
	<label for="search">Nombre: </label>
	<input id="complexName" type="text" maxlength="200" name="search"/>
	<input type="submit" value="Buscar" class="submit-go" />
	</fieldset>
</form>


<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />