<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp" flush="true">
	<jsp:param name="title" value="Confirmar reserva periódica" />
</jsp:include>

<h1>Confirmar reserva periódica</h1>

	<div class="ui-state-highlight ui-corner-all info"> 
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Información:</strong></span>
					<span class="block">* Hay días en los cuales la cancha está resevada, si desea reservar para los días en los que no está ocupada presione confirmar</span>
					<span class="block">* Desde: <c:out value="${from}"/></span>
					<span class="block">* Hasta: <c:out value="${to}"/></span>
					<span class="block">* Horario: <c:out value="${when}"/> </span>
					
	</div>

<form action="" method="post">
	<c:if test="${(from != null)}">
		<input type="hidden" name="from" size="10" value="<c:out value="${from}"/>"/>
	</c:if>
	<c:if test="${(to != null)}">
		<input type="hidden" name="to" size="10" value="<c:out value="${to}"/>"/>
	</c:if>
	<c:if test="${(when != null)}">
		<input type="hidden" name="when" value="<c:out value="${when}"/>"/>
	</c:if>
	<c:if test="${(id != null)}">
		<input id="id" type="hidden" name="id" value="<c:out value="${id}"/>"/>
	</c:if>
	
	<input type="hidden" name="check" value="false"/>
	<fieldset>
	<legend>Confirmación</legend>
		<input type="submit" value="Confirmar" class="submit-go" />
	</fieldset>
</form>



<jsp:include page="/WEB-INF/views/general/js.jsp"
	flush="true" />

<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />