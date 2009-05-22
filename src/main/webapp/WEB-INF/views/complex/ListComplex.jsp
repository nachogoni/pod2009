<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp" flush="true">
	<jsp:param name="title" value="Listado de Complejos" />
	<jsp:param name="header" value="admin" />
</jsp:include>

<h1>Esto es un puto listado</h1>

<table class="complexesTable" border="1">
	<tr>
		<td>Nombre</td>
		<td>Lugar</td>
		<td>
			<form action="AddComplex" method="get">
				<input type="submit" name="add" value="Agregar Nuevo"/>
			</form>
		</td>
 	</tr>
	<c:if test="${(complexes != null)}">
		<c:forEach items="${complexes}" var="complex">
			<form action="DeleteComplex" method="post">
			
			<!--  TODO: Arreglar esta grasada que hacemos para pasar el parametro -->
			
			<input type="hidden" name="id" value="<c:out value="${complex.id}"/>"/>
			<tr>
				<td><c:out value="${complex.id}" /></td>
				<td><c:out value="${complex.name}" /></td>
				<td><c:out value="${complex.place}" /></td>
				<td><input type="submit" name="delete" value="Eliminar"/></td>
			</tr>
			</form>
		</c:forEach>
	</c:if>
</table>

</body>
</html>