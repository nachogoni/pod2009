<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp" flush="true">
	<jsp:param name="title" value="Agregar un nuevo complejo" />
	<jsp:param name="header" value="admin" />
</jsp:include>

<h1>Agregar un nuevo Complejo</h1>


<c:out value="${formulario}" escapeXml="false" />

<jsp:include page="/WEB-INF/views/general/footer/footer.jsp"
	flush="true" />