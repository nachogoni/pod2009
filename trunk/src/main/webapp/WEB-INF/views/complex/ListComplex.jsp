<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Esto es un puto listado</h1>

<table class="complexesTable" border="1">
	<tr>
		<td>Nombre</td>
		<td>Lugar</td>
	</tr>
	<c:if test="${(complexes != null)}">
		<c:forEach items="${complexes}" var="complex">
			<form action="/DeleteComplex" method="post">
			
			<!--  TODO: Arreglar esta grasada que hacemos para pasar el parametro -->
			
			<input type="hidden" name="id" value="<c:out value="${complex.id}" />"/>
			<tr>
				<td><c:out value="${complex.id}" /></td>
				<td><c:out value="${complex.name}" /></td>
				<td><c:out value="${complex.place}" /></td>
				<td><input type="submit" name="delete" value="Delete"/></td>
			</tr>
			</form>
		</c:forEach>
	</c:if>
</table>


</body>
</html>