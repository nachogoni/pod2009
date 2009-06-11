<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp"
	flush="true">
	<jsp:param name="title" value="Sistema de Puntos" />
</jsp:include>


<jsp:include page="/WEB-INF/views/general/js.jsp" flush="true" />

<h1>Sistema de Puntos</h1>

<c:if test="${(param.modify != null)}">

	<c:choose>
		<c:when test="${param.modify}">
			<c:set var="modifyMsg" scope="page"
				value="El sistema de puntos fue modificado exitosamente" />
		</c:when>
		<c:otherwise>
			<c:set var="modifyMsg" scope="page"
				value="Error al modificar el sistema de puntos" />
		</c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"><span
		class="ui-icon ui-icon-info infoIcon"></span> <span><strong>Información:</strong></span>
	<span class="block">* <c:out value="${modifyMsg}" /></span></div>
</c:if>


<table class="scoreSystemTable" border="1">
	<tr>
		<td><strong>Reservar</strong></td>
		<td><strong>Señar</strong></td>
		<td><strong>Pagar completamente</strong></td>
		<td><strong>Cancelar una reserva</strong></td>
		<td><strong>Cancelar una reserva señada</strong></td>
		<td></td>
	</tr>
	<tr class="<c:out value="${rowStyle}" />">
		<td><c:out value="${scoreSystem.booking}" /></td>
		<td><c:out value="${scoreSystem.deposit}" /></td>
		<td><c:out value="${scoreSystem.pay}" /></td>
		<td><c:out value="${scoreSystem.downBooking}" /></td>
		<td><c:out value="${scoreSystem.downDeposit}" /></td>
		<td>
		<form action="<c:out value="${baseURI}" />/scoresystem/modify"
			method="get"><!--  TODO: Arreglar esto que hacemos para pasar el parametro -->
		<input type="submit" name="modify" value="Modificar" /></form>
		</td>
	</tr>
</table>


<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />