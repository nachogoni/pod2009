<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp" flush="true">
	<jsp:param name="title" value="Listado de RSS" />
</jsp:include>

<h2>Listado de RSS disponibles<c:if test="${(province != null)}"> - <c:out value="${province}" /></c:if><c:if test="${(locality != null)}"> - <c:out value="${locality}" /></c:if><c:if test="${(neighbourhood != null)}"> - <c:out value="${neighbourhood}" /></c:if></h2>

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
	<c:when test="${(province == null)}">
		<a href="<c:out value="${baseURL}" />/<c:out value="${rss}" />"><c:out value="${rss}" /></a>
	</c:when>
	<c:when test="${(locality == null)}">
		<a href="<c:out value="${baseURL}" />/<c:out value="${rss}" />?province=<c:out value="${province}" />"><c:out value="${rss}" /></a>
	</c:when>
	<c:when test="${(neighbourhood == null)}">
		<a href="<c:out value="${baseURL}" />/<c:out value="${rss}" />?province=<c:out value="${province}" />&locality=<c:out value="${locality}" />"><c:out value="${rss}" /></a>
	</c:when>
	<c:otherwise>	
		<a href="<c:out value="${baseURL}" />/<c:out value="${rss}" />?province=<c:out value="${province}" />&locality=<c:out value="${locality}" />&neighbourhood=<c:out value="${neighbourhood}" />"><c:out value="${rss}" /></a>
	</c:otherwise>
	</c:choose>
</c:forEach>

<form action="" method="get">
	<fieldset>
		<legend>Por favor, seleccione una provincia de la que quiera la información detallada</legend>
		<label for="province">Provincia: </label>
		<select id="province" name="province">
			<option value="">Todas...</option>
			<c:forEach items="${provinces}" var="p" varStatus="rowCounter">
				<option value="<c:out value="${p}" />"><c:out value="${p}" /></option>
			</c:forEach>
		</select>
		<input type="submit" value="Elegir" class="submit-go" />
	</fieldset>
</form>
<c:if test="${(province != null)}">
	<form action="" method="get">
		<fieldset>
			<legend>Por favor, seleccione la localidad de la que quiera la información detallada</legend>
			<label for="locality">Localidad: </label>
			<select id="locality" name="locality">
				<option value="">Todas...</option>
				<c:forEach items="${locations}" var="l" varStatus="rowCounter">
					<option value="<c:out value="${l}" />"><c:out value="${l}" /></option>
				</c:forEach>
			</select>
			<input id="province" type="hidden" name="province" value="<c:out value="${province}"/>"/>
			<input type="submit" value="Elegir" class="submit-go" />
		</fieldset>
	</form>
	<c:if test="${(locality != null)}">
		<form action="" method="get">
			<fieldset>
				<legend>Por favor, seleccione un barrio del que quiera la información detallada</legend>
				<label for="neighbourhood">Barrio: </label>
				<select id="neighbourhood" name="neighbourhood">
					<option value="">Todos...</option>
					<c:forEach items="${neighbourhoods}" var="n" varStatus="rowCounter">
						<option value="<c:out value="${n}" />"><c:out value="${n}" /></option>
					</c:forEach>
				</select>
				<input id="province" type="hidden" name="province" value="<c:out value="${province}"/>"/>
				<input id="locality" type="hidden" name="locality" value="<c:out value="${locality}"/>"/>
				<input type="submit" value="Elegir" class="submit-go" />
			</fieldset>
		</form>
	</c:if>
</c:if>

<jsp:include page="/WEB-INF/views/general/js.jsp"
	flush="true" />

<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />