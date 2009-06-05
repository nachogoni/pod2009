<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp" flush="true">
	<jsp:param name="title" value="Modificar Perfil" />
</jsp:include>

<h1>Modificar Perfil</h1>

<c:if test="${(param.success != null)}">

	<c:choose>
	  <c:when test="${param.success}">
	    <c:set var="successMsg" scope="page" value="El perfil fue modificado exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="successMsg" scope="page" value="Error al modificar el perfil"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Información:</strong></span>
		<span class="block">* <c:out value="${successMsg}" /></span>
	</div>
</c:if>

<c:if test="${(errorManager != null)}">
<div class="ui-state-error ui-corner-all error"> 

<span class="ui-icon ui-icon-alert errorIcon"></span>
<span>
<strong>Alerta:</strong>
</span>
		<c:forEach items="${errorManager.errors}" var="error">
			<span class="block">* <c:out value="${error}"/></span>
		</c:forEach>
</div>
</c:if>

<c:out value="${formulario}" escapeXml="false" />

<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />