<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp"
	flush="true">
	<jsp:param name="title" value="Panel de administración" />
	<jsp:param name="header" value="admin" />
</jsp:include>

<h1>Panel de administración</h1>

<h3>¡Bienvenido a la canchita! Aquí podrá administrar sus complejos.</h3>

<form action="/tp-pod/ListComplex" method="get">
	<fieldset>
	<legend>Búsqueda de complejos</legend>
	<label for="search">Nombre: </label>
	<input id="complexName" type="text" name="search"/>
	</fieldset>

	<input type="submit" value="Buscar" class="submit-go" />
</form>


<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />