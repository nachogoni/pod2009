<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp" flush="true">
	<jsp:param name="title" value="Modificar Complejo" />
</jsp:include>

<h1>Agregar Política de Expiración para la cancha</h1>

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

<jsp:include page="/WEB-INF/views/general/js.jsp"
    flush="true" />
<script src="<c:out value="${baseURI}" escapeXml="false" />/js/complex/add/init.js" type="text/javascript"/></script>

<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
    flush="true" />