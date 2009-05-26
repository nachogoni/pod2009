<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp" flush="true">
	<jsp:param name="title" value="Modificar Complejo" />
	<jsp:param name="header" value="admin" />
</jsp:include>

<h1>Modificar Complejo</h1>



<form action="" method="post">

	<fieldset>
		<legend>Complejo</legend>
		<label for="name">Nombre: </label>
		<input type="text" name="name" value="<c:out value="${complex.name}" />" size="30"/>
		<label for="description">Descripción: </label>
		<input type="text" name="description" value="<c:out value="${complex.description}" />" size="30"/>
	</fieldset>

	<fieldset>
		<legend>Ubicación</legend>
		<label for="address">Direccion: </label>
		<input type="text" name="address" value="<c:out value="${complex.place.address}" />" size="60"/>
		<label for="neighbourhood">Barrio: </label>
		<input type="text" name="neighbourhood" value="<c:out value="${complex.place.neighbourhood}" />" size="30"/>
		<label for="town">Ciudad: </label>
		<input type="text" name="town" value="<c:out value="${complex.place.town}" />" size="30"/>
		<label for="state">Provincia: </label>
		<input type="text" name="state" value="<c:out value="${complex.place.state}" />" size="30"/>
		<label for="zipcode">Código Postal: </label>
		<input type="text" name="zipcode" value="<c:out value="${complex.place.zipCode}" />" size="30"/>
		<label for="latitude">Latitud: </label>
		<input type="text" name="latitude" value="<c:out value="${complex.place.latitude}" />" size="30"/>
		<label for="longitude">Longitud: </label>
		<input type="text" name="longitude" value="<c:out value="${complex.place.longitude}" />" size="30"/>
		<label for="telephone">Teléfono: </label>
		<input type="text" name="telephone" size="30"/>
		<label for="country">País: </label>
		<jsp:include page="/WEB-INF/resources/countries_es.html" flush="true"/>
   	</fieldset>

	<fieldset>
		<legend>Sistema de Puntos</legend>
		<label for="address">Reservar: </label>
		<input type="text" name="booking" value="<c:out value="${complex.scoreSystem.booking}" />" size="30"/>
		<label for="neighbourhood">Seniar: </label>
		<input type="text" name="deposit" value="<c:out value="${complex.scoreSystem.deposit}" />" size="30"/>
		<label for="town">Pagar: </label>
		<input type="text" name="pay" value="<c:out value="${complex.scoreSystem.pay}" />" size="30"/>
		<label for="state">Cancelar Reserva: </label>
		<input type="text" name="downBooking" value="<c:out value="${complex.scoreSystem.downBooking}" />" size="30"/>
		<label for="zipcode">Cancelar Deposito: </label>
		<input type="text" name="downDeposit" value="<c:out value="${complex.scoreSystem.downDeposit}" />" size="30"/>
   	</fieldset>   	
   	<input type="hidden" name="id" value="<c:out value="${complex.id}"/>" />
	<input type="submit" value="Actualizar" class="submit-go">
</form>

<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />