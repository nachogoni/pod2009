<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<div id="header" class="header">
<div id="menu" class="ubercolortabs loggedColorTab">
<ul class="ui-tabs-nav">
	<li><a href="<c:out value="${baseURI}" />/user/home"><span>Inicio</span></a></li>
	<li><a href="<c:out value="${baseURI}" />/ListComplex"><span>Complejos</span></a></li>
	<li><a href="<c:out value="${baseURI}" />/field/list"><span>Canchas</span></a></li>
	<li><a href="<c:out value="${baseURI}" />/user/profile"><span>Perfil</span></a></li>
	<li><a href="<c:out value="${baseURI}" />/ListRSS"><span>RSS</span></a></li>
	<li><a href="<c:out value="${baseURI}" />/user/logout"><span>Cerrar Sesión</span></a></li>
</ul>
</div>

<div class="userPointsHeader">Hola <b><c:out value="${user.username}"/></b>! [<c:out value="${user.score}"/> puntos]</div>
<div class="ubercolordivider"></div>
<div><img alt="micanchita.com" class="image" src="../img/logo.jpg" /></div>
        <div class="ubercolordivider"> </div>
</div>