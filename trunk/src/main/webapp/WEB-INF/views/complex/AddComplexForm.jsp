<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp" flush="true">
	<jsp:param name="title" value="Agregar un nuevo complejo" />
	<jsp:param name="header" value="admin" />
</jsp:include>

<h1>Agregar un nuevo Complejo</h1>


<c:out value="${formulario}" escapeXml="false" />

<form action="" method="post">

	<fieldset>
		<legend>Complejo</legend>
		<label for="name">Nombre: </label>
		<input type="text" name="name" size="30"/>
		<label for="description">Descripción: </label>
		<input type="text" name="description" size="30"/>
	</fieldset>

	<fieldset>
		<legend>Ubicación</legend>
		<label for="address">Direccion: </label>
		<input type="text" name="address" size="60"/>
		<label for="neighbourhood">Barrio: </label>
		<input type="text" name="neighbourhood" size="30"/>
		<label for="town">Ciudad: </label>
		<input type="text" name="town" size="30"/>
		<label for="state">Provincia: </label>
		<input type="text" name="state" size="30"/>
		<label for="zipcode">Código Postal: </label>
		<input type="text" name="zipcode" size="30"/>
		<label for="latitude">Latitud: </label>
		<input type="text" name="latitude" size="30"/>
		<label for="longitude">Longitud: </label>
		<input type="text" name="longitude" size="30"/>
		<label for="telephone">Teléfono: </label>
		<input type="text" name="telephone" size="30"/>
		<label for="country">País: </label>
		<jsp:include page="/WEB-INF/resources/countries_es.html" flush="true"/>
   	</fieldset>

	<fieldset>
		<legend>Sistema de Puntos</legend>
		<label for="address">Reservar: </label>
		<input type="text" name="booking" size="30"/>
		<label for="neighbourhood">Seniar: </label>
		<input type="text" name="deposit" size="30"/>
		<label for="town">Pagar: </label>
		<input type="text" name="pay" size="30"/>
		<label for="state">Cancelar Reserva: </label>
		<input type="text" name="downBooking" size="30"/>
		<label for="zipcode">Cancelar Deposito: </label>
		<input type="text" name="downDeposit" size="30"/>
   	</fieldset>   	
   	
	<input type="submit" value="Agregar" class="submit-go">
</form>

<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />