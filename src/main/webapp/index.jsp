
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp"
	flush="true">
	<jsp:param name="title" value="micanchita.com" />
	<jsp:param name="header" value="guest" />
</jsp:include>

<h1>Est�s entrando en la canchita...</h1>

<h3>�Busc� un complejo amigo y reserv� tu cancha en segundos!</h3>

<img alt="micanchita.com" class="image" src="img/fieldPortada.jpg" />
<form action="/tp-pod/ListComplex" method="get">
	<fieldset>
	<legend>B�squeda de complejos</legend>
	<label for="search">Nombre: </label>
	<input id="complexName" type="text" name="search"/>
	<input type="submit" value="Buscar" class="submit-go" />
	</fieldset>
</form>


<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />