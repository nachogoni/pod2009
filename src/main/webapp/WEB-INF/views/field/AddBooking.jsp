<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp" flush="true">
	<jsp:param name="title" value="Realizar reserva" />
	<jsp:param name="header" value="logged" />
</jsp:include>

<h1>Realizar la reserva de la cancha</h1>

<c:if test="${(errorManager != null)}">
<div class="ui-state-error ui-corner-all"> 

<span class="ui-icon ui-icon-alert errorIcon"></span>
<span>
<strong>Alerta:</strong>
</span>
		<c:forEach items="${errorManager.errors}" var="error">
			<span class="block">* <c:out value="${error}"/></span>
		</c:forEach>
</div>
</c:if>

<form action="" method="post">
	<fieldset>
	<legend>Por favor, seleccione la fecha en la que desea realizar la reserva</legend>
	<label for="name">Fecha: </label>
	<input id="datepicker" type="text" name="date" size="10"/>
	</fieldset>
	
	<fieldset id="whenForm" >
	<legend>Ahora, seleccione el horario de entre los disponibles</legend>
	<select id="when" name="when"></select>
	</fieldset>
	
	<c:if test="${(id != null)}">
		<input id="id" type="hidden" name="id" value="<c:out value="${id}"/>"/>
	</c:if>

	<input type="submit" value="Agregar" class="submit-go" />
</form>

<script src="/tp-pod/js/jquery/js/jquery-1.3.2.min.js" type="text/javascript"/></script>
<script src="/tp-pod/js/jquery/js/jquery-ui-1.7.1.custom.min.js" type="text/javascript"/></script>
<script src="/tp-pod/js/util/calendar.js" type="text/javascript"/></script>
<script src="/tp-pod/js/field/book/init.js" type="text/javascript"/></script>

</body>
</html>