<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Agregar un nuevo Complejo</title>
</head>
<body>

<h1>Agregar un nuevo Complejo</h1>


<FORM action="" method="post">

	<fieldset>
		<legend>Complejo</legend>
		<label for="name">Nombre: </label>
		<input type="text" name="name" size="30"/>
		<label for="description">Descripción: </label>
		<input type="text" name="description" size="30"/>
	</fieldset>

	<fieldset>
		<legend>Horarios de atención</legend>
			<%  int h = 0;  %>
			<fieldset>
				<legend>Lunes</legend>
				<label for="mondayOpenHour">Abre: </label>
				<select name ="mondayOpenHour">
					<%	for(int y=0; y<24; y++) { %>
					<option><%=y%></option>
					<%  }   %>
				</select>
				:
				<select name ="mondayOpenMinute">
					<%  h = 0;  %>
					<%	for(int y=0; y < 4; y++) { %>
					<option><%= h %></option>
					<%  h += 15; }   %>
				</select>

				<label for="mondayOpenHour">Cierra: </label>
				<select name ="mondayCloseHour">
					<%	for(int y=0; y<24; y++) { %>
					<option><%=y%></option>
					<%  }   %>
				</select>
				:
				<select name ="mondayCloseMinute">
					<%  h = 0;  %>
					<%	for(int y=0; y < 4; y++) { %>
					<option><%= h %></option>
					<%  h += 15; }   %>
				</select>
			</fieldset>
	</fieldset>

	<fieldset>
		<legend>Ubicación</legend>
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
   	
	<input type="submit" value="Agregar">
</form>

</body>
</html>