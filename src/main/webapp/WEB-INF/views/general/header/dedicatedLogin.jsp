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
<link rel="stylesheet"
	href="/tp-pod/js/jquery/css/custom-theme/jquery-ui-1.7.1.custom.css"
	type="text/css" media="print, projection, screen">
<link rel="stylesheet"
	href="/tp-pod/js/jquery/js/jquery-tooltip/jquery.tooltip.css"
	type="text/css" media="print, projection, screen">
	

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Canchita - <c:out value="${param.title}" /></title>
</head>
<body class="bloggerStyle">
<div id="header" class="header">
        <div id="menu" class="ubercolortabs guestColorTab">
                <ul class="ui-tabs-nav">
                        <li><a href="<c:out value="${baseURI}" />/"><span>Inicio</span></a></li>
                        <li><a href="<c:out value="${baseURI}" />/ListComplex"><span>Complejos</span></a></li>
                        <li><a href="<c:out value="${baseURI}" />/field/list"><span>Canchas</span></a></li>
                        <li><a href="<c:out value="${baseURI}" />/user/register"><span>Registrarse</span></a></li>
                </ul>
        </div>

        <div class="ubercolordivider"> </div>
        <div><img alt="micanchita.com" class="image" src="img/logo.jpg" /></div>
        <div class="ubercolordivider"> </div>
</div>