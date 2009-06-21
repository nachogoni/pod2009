<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/js.jsp" flush="true" />
<script src="<c:out value="${baseURI}" escapeXml="false" />/js/login/init.js" type="text/javascript"/></script>

<div id="header" class="header">
        <div id="menu" class="ubercolortabs guestColorTab">
                <ul class="ui-tabs-nav">
                        <li><a href="<c:out value="${baseURI}" />/"><span>Inicio</span></a></li>
                        <li><a href="<c:out value="${baseURI}" />/ListComplex"><span>Complejos</span></a></li>
                        <li><a href="<c:out value="${baseURI}" />/field/list"><span>Canchas</span></a></li>
                        <li><a href="<c:out value="${baseURI}" />/user/register"><span>Registrarse</span></a></li>
                </ul>
        </div>

        <div class="loginForm">
                <FORM action="<c:out value="${baseURI}" />/user/login" method="post" >
                        <INPUT id="lf_username" type="text" maxlength="200" name="username" value="Usuario" class="loginInputElement gray">
                        <INPUT id="lf_password" type="password" maxlength="200" name="password" value="********" class="loginInputElement gray">
                        <INPUT class="submit-go" type="submit" value="Ingresar" class="loginButtonElement">
                </FORM>
        </div>
        <div class="ubercolordivider"> </div>
</div>