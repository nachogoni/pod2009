<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page language="java" isErrorPage="true" %>

<jsp:include page="/WEB-INF/views/general/header/header.jsp" flush="true">
	<jsp:param name="title" value="Página Inexistente" />
</jsp:include>

<h1>Error en el servidor</h1>

<div class="ui-state-error ui-corner-all error"> 

<span class="ui-icon ui-icon-alert errorIcon"></span>
<span>
<strong>Alerta:</strong>
</span>
<span class="block">* ¡Ocurrió un error en el servidor, por favor intente nuevamente!</span>
</div>

<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />