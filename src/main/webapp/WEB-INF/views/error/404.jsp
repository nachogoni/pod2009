<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page language="java" isErrorPage="true" %>

<jsp:include page="/WEB-INF/views/general/header/header.jsp" flush="true">
	<jsp:param name="title" value="Error en el servidor" />
</jsp:include>

<h1>P�gina Inexistente</h1>

<div class="ui-state-error ui-corner-all error"> 

<span class="ui-icon ui-icon-alert errorIcon"></span>
<span>
<strong>Alerta:</strong>
</span>
<span class="block">* �La p�gina a la que intentaste acceder no existe!</span>
</div>

<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />