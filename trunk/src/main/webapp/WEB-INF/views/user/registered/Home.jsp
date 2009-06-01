<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<c:choose>
	<c:when test="${user.isAdmin}">
		<jsp:include page="/WEB-INF/views/user/registered/admin/AdminHome.jsp"
			flush="true" />
	</c:when>
	<c:otherwise>
		<jsp:include page="/WEB-INF/views/user/registered/commonUser/UserHome.jsp"
			flush="true" />
	</c:otherwise>
</c:choose>
