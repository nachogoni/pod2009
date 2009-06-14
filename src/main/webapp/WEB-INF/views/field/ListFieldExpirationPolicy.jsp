<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp"
	flush="true">
	<jsp:param name="title" value="Pol�ticas de expiraci�n para la cancha" />
	<jsp:param name="header" value="admin" />
</jsp:include>

<jsp:include page="/WEB-INF/views/general/js.jsp"
	flush="true" />
<script src="<c:out value="${baseURI}" escapeXml="false"/>/js/util/delete.js" type="text/javascript"/></script>

<h1>Pol�ticas de expiraci�n para la cancha</h1>

<div class="ui-state-highlight ui-corner-all info"> 
		
	<span class="ui-icon ui-icon-info infoIcon"></span>
	<span><strong>Informaci�n:</strong></span>
	<span class="block">* Recuerde que la pol�tica que se aplicar� es la �ltima que coincide
	con el puntaje actual del usuario. Si la cancha no posee una pol�tica que coincida se
	utilizar� aquella que haya definido el complejo.</span>
</div>

<c:choose>
<c:when test="${(policies != null)}">

	<c:choose>
	<c:when test="${ quantity != 0 }">
		<table class="policiesTable" border="1">
			<tr>
				<td><strong>Puntaje desde</strong></td>
				<td><strong>Puntaje hasta</strong></td>
				<td><strong>D�as que soporta en estado se�ada</strong></td>
				<td><strong>D�as que soporta en estado reservada</strong></td>
				<td>
				<form action="<c:out value="${baseURI}" />/field/AddFieldExpirationPolicy" method="get">
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
							<form action="<c:out value="${baseURI}" />/field/DeleteFieldExpirationPolicy" method="post">
							<input type="hidden" name="id" value="<c:out value="${policy.id}"/>" />
							<input type="submit" class="delete" name="delete" value="Eliminar" />
							</form>
							<form action="<c:out value="${baseURI}" />/field/ModifyFieldExpirationPolicy" method="get">
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
				<span><strong>Informaci�n:</strong></span>
				<span class="block">* Hasta el momento no ha definido ninguna pol�tica.</span>

			</div>
						
			<div class="submit-go">
		        <a href="<c:out value="${baseURI}"/>/field/AddFieldExpirationPolicy?id=<c:out value="${param.id}"/>">Agregar</a>
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
