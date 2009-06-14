<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp"
	flush="true">
	<jsp:param name="title" value="Políticas de expiración para el complejo" />
	<jsp:param name="header" value="admin" />
</jsp:include>

<jsp:include page="/WEB-INF/views/general/js.jsp"
	flush="true" />
<script src="<c:out value="${baseURI}" escapeXml="false"/>/js/util/delete.js" type="text/javascript"/></script>

<h1>Políticas de expiración</h1>

<%-- TODO generalizar esto --%>

<c:if test="${(param.add != null)}">

	<c:choose>
	  <c:when test="${param.add}">
	    <c:set var="addMsg" scope="page" value="La política fue agregada exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="addMsg" scope="page" value="Error al agregar la política"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Información:</strong></span>
		<span class="block">* <c:out value="${addMsg}" /></span>
	</div>
</c:if>

<c:if test="${(param.modify != null)}">

	<c:choose>
	  <c:when test="${param.modify}">
	    <c:set var="modifyMsg" scope="page" value="La política fue modificada exitosamente"/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="modifyMsg" scope="page" value="Error al modificar la política"/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Información:</strong></span>
		<span class="block">* <c:out value="${modifyMsg}" /></span>
	</div>
</c:if>


<c:if test="${(param.delete != null)}">

	<c:choose>
	  <c:when test="${param.delete}">
	    <c:set var="deleteMsg" scope="page" value="La política fue eliminada exitosamente."/>
	  </c:when>
	  <c:otherwise>
	    <c:set var="deleteMsg" scope="page" value="Error al eliminar la política de expiración."/>
	  </c:otherwise>
	</c:choose>

	<div class="ui-state-highlight ui-corner-all info"> 
			
		<span class="ui-icon ui-icon-info infoIcon"></span>
		<span><strong>Información:</strong></span>
		<span class="block">* <c:out value="${deleteMsg}" /></span>
	</div>
</c:if>


<c:choose>
<c:when test="${(policies != null)}">

	<c:choose>
	<c:when test="${ quantity != 0 }">
		<table class="policiesTable" border="1">
			<tr>
				<td><strong>Desde</strong></td>
				<td><strong>Hasta</strong></td>
				<td><strong>Días que soporta en estado señada</strong></td>
				<td><strong>Días que soporta en estado reservada</strong></td>
				<td>
				<form action="<c:out value="${baseURI}" />/AddExpirationPolicy" method="get">
				<input type="hidden" name="id" value="<c:out value="${param.id}"/>" />
				<input type="submit" value="Agregar nueva" />
				</form>
				</td>
			</tr>
			<c:forEach items="${policies}" var="policy" varStatus="rowCounter">
					
			        <c:choose>
			          <c:when test="${rowCounter.count % 2 == 0}">
			            <c:set var="rowStyle" scope="page" value="odd"/>
			          </c:when>
			          <c:otherwise>
			            <c:set var="rowStyle" scope="page" value=""/>
			          </c:otherwise>
			        </c:choose>
					
					<tr class="<c:out value="${rowStyle}" />">
						<td><c:out value="${policy.scoreFrom}" /></td>
						<td><c:out value="${policy.scoreTo}" /></td>
						<td><c:out value="${policy.depositLimit}" /></td>
						<td><c:out value="${policy.bookingLimit}" /></td>
						
						<td>
							<form action="<c:out value="${baseURI}" />/DeleteExpirationPolicy" method="post">
							<input type="hidden" name="id" value="<c:out value="${policy.id}"/>" />
							<input type="submit" class="delete" name="delete" value="Eliminar" />
							</form>
							<form action="<c:out value="${baseURI}" />/ModifyExpirationPolicy" method="get">
							<input type="hidden" name="id" value="<c:out value="${policy.id}"/>" />
							<input type="submit" name="modify" value="Modificar" />
							</form>
						</td>
					</tr>
					
				</c:forEach>
		</table>
		</c:when>
		<c:otherwise>
			<div class="ui-state-highlight ui-corner-all info"> 
			
				<span class="ui-icon ui-icon-info infoIcon"></span>
				<span><strong>Información:</strong></span>
				<span class="block">* No se encontraron políticas de expiración.</span>

			</div>
						
			<div class="submit-go">
		        <a href="<c:out value="${baseURI}"/>/AddExpirationPolicy?id=<c:out value="${param.id}"/>">Agregar</a>
	        </div>


		</c:otherwise>
		</c:choose>
</c:when>
<c:otherwise>
	<div class="ui-state-error ui-corner-all error"> 
	
	<span class="ui-icon ui-icon-alert errorIcon"></span>
	<span>
	<strong>Alerta:</strong>
	</span>
		<span class="block">* Error en el servidor</span>
	</div>
</c:otherwise>
</c:choose>


<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />
