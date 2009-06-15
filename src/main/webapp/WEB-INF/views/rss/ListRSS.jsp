<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp" flush="true">
	<jsp:param name="title" value="Listado de RSS" />
</jsp:include>

<h1>Listado de RSS disponibles</h1>

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

<c:forEach items="${rsss}" var="rss" varStatus="rowCounter">


<c:choose>
<c:when test="${(neighbourhood != null)}">
	<a href="<c:out value="${baseURL}" />/<c:out value="${rss}" />?neighbourhood=<c:out value="${neighbourhood}" />"><c:out value="${rss}" /></a>
</c:when>
<c:otherwise>	
	<a href="<c:out value="${baseURL}" />/<c:out value="${rss}" />"><c:out value="${rss}" /></a>
</c:otherwise>
</c:choose>

</c:forEach>


<form action="" method="get">
	<fieldset>
	<legend>Por favor, seleccione un barrio del que quiera la información detallada</legend>
	<label for="neighbourhood">Barrio: </label>
	<select id="neighbourhood" name="neighbourhood">
		<option value="">Todos...</option>
		<c:forEach items="${neighbourhoods}" var="neighbour" varStatus="rowCounter">
			<option value="<c:out value="${neighbour}" />"><c:out value="${neighbour}" /></option>
		</c:forEach>
	</select>
	</fieldset>

	<div id="whenError" class="ui-state-error ui-corner-all hidden error"> 
		<span class="ui-icon ui-icon-alert errorIcon"></span>
		<span><strong>Alerta:</strong></span>
		<span class="block">* No hay horarios disponibles para este día</span>
	</div>

	<div id="whenJSONError" class="ui-state-error ui-corner-all hidden error"> 
	
		<span class="ui-icon ui-icon-alert errorIcon"></span>
		<span><strong>Alerta:</strong></span>
		<span id="whenJSONErrorMsg" class="block"></span>
	</div>
		
	<input type="submit" value="Elegir" class="submit-go" />
</form>



<jsp:include page="/WEB-INF/views/general/js.jsp"
	flush="true" />

<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />