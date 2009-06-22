<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<html>
<head>
<link href="/tp-pod/img/favicon.ico" rel="icon" type="image/x-icon" />
<link rel="stylesheet" href="/tp-pod/css/color.tabs.css" type="text/css"
	media="print, projection, screen">
<link rel="stylesheet" href="/tp-pod/css/greentable.css" type="text/css"
	media="print, projection, screen">
<link rel="stylesheet" href="/tp-pod/css/canchita.css" type="text/css"
	media="print, projection, screen">
<link rel="stylesheet"
	href="/tp-pod/js/jquery/css/custom-theme/jquery-ui-1.7.1.custom.css"
	type="text/css" media="print, projection, screen">
<link rel="stylesheet"
	href="/tp-pod/js/jquery/js/jquery-tooltip/jquery.tooltip.css"
	type="text/css" media="print, projection, screen">
<link rel="stylesheet"
	href="/tp-pod/js/jquery/js/jquery-timepickr/jquery.timepickr.css"
	type="text/css" media="print, projection, screen">	


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Canchita - <c:out value="${param.title}" /></title>
</head>
<body class="bloggerStyle">

<c:choose>
	<c:when test="${user.isGuest}">
		<jsp:include page="/WEB-INF/views/general/header/guest.jsp"
			flush="true" />
	</c:when>
	<c:otherwise>

		<c:choose>
			<c:when test="${user.isAdmin}">
				<jsp:include page="/WEB-INF/views/general/header/admin.jsp"
					flush="true" />
			</c:when>
			<c:otherwise>
				<jsp:include page="/WEB-INF/views/general/header/logged.jsp"
					flush="true" />
			</c:otherwise>
		</c:choose>

	</c:otherwise>
</c:choose>