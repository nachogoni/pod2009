<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp"
	flush="true">
	<jsp:param name="title" value="Panel de administraci�n" />
</jsp:include>

<h1>Panel de administraci�n</h1>

<h3>�Hola <c:out value="${user.username}" />! Bienvenido a la canchita. Aqu� podr� administrar sus complejos.</h3>

<form action="<c:out value="${baseURI}" />/ListComplex" method="get">
	<fieldset>
	<legend>B�squeda de complejos</legend>
	<label for="search">Nombre: </label>
	<input id="complexName" type="text" name="search"/>
	<input type="submit" value="Buscar" class="submit-go" />
	</fieldset>

</form>


<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />