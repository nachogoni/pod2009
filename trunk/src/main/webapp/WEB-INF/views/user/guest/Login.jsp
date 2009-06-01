<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp" flush="true">
	<jsp:param name="title" value="Ingresar al sistema" />
</jsp:include>

<h1>Ingresar al sistema</h1>

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

    <div class="loginForm">
            <FORM action="" method="post" >
                    <INPUT type="text" name="username" value="Usuario" class="loginInputElement gray">
                    <INPUT type="password" name="password" value="********" class="loginInputElement gray">
                    <INPUT class="submit-go" type="submit" value="Ingresar" class="loginButtonElement">
            </FORM>
    </div>
<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
    flush="true" />