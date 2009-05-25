<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<html>
<head>

<link rel="stylesheet" href="/tp-pod/css/color.tabs.css" type="text/css"
	media="print, projection, screen">
<link rel="stylesheet" href="/tp-pod/css/greentable.css" type="text/css"
	media="print, projection, screen">
<link rel="stylesheet" href="/tp-pod/css/canchita.css" type="text/css"
	media="print, projection, screen">
<link rel="stylesheet" href="/tp-pod/js/jquery/css/custom-theme/jquery-ui-1.7.1.custom.css" type="text/css"
	media="print, projection, screen">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%="Canchita - " + request.getParameter("title")%></title>
</head>
<body class="bloggerStyle">
<jsp:include
	page="<%="/WEB-INF/views/general/header/" + request.getParameter("header") + ".jsp"%>"
	flush="true">
	<jsp:param name="title" value="Listado de Complejos" />
</jsp:include>