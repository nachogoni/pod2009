<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<div id="header" class="header">
<div id="menu" class="ubercolortabs adminColorTab">
<ul class="ui-tabs-nav">
	<li><a href="<c:out value="${baseURI}" />/user/home"><span>Panel</span></a></li>
	<li><a href="<c:out value="${baseURI}" />/ListComplex"><span>Complejos</span></a></li>
	<li><a href="<c:out value="${baseURI}" />/field/list"><span>Canchas</span></a></li>
	<li><a href="#"><span>Reservas</span></a></li>
	<li><a href="<c:out value="${baseURI}" />/scoresystem/ShowScoreSystem"><span>Sistema de Puntos</span></a></li>
	<li><a href="<c:out value="${baseURI}" />/admin/email"><span>E-mail</span></a></li>
	<li><a href="<c:out value="${baseURI}" />/user/logout"><span>Cerrar Sesión</span></a></li>
</ul>
</div>

<div class="ubercolordivider"></div>
</div>

