<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/start.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/start.menu.css" type="text/css">
<title>Inicio</title>
</head>
<body>
<%
    UserService userService = UserServiceFactory.getUserService();
%>
	<div id='cssmenu'>
		<ul>
		   <li class='has-sub'><a href='#'><span>${sessionScope.nickname}</span></a>
		      <ul>
		         <li><a href="<%= userService.createLogoutURL("/") %>"><span>Salir</span></a></li>
		      </ul>
		   </li>
		</ul>
	</div>
	
	<div id="headerlogo"><span style="vertical-align: sub;">STOCK-IN&nbsp;</span>
		<img src="/img/logo.png" width="30" height="30" border="0" style="float: right; margin-top: 3px; margin-right: 2px;">
	</div><br />
	<p></p>
	
	<div class="menu">
		<div class="row">
			<div class="cell">
				<div class="option catalogos" onclick="window.location='catalogs.mvc';">
					<img alt="catalogos" src="${pageContext.request.contextPath}/img/catalogo-icon.png"><br />
					<span>Catálogos</span>
				</div>
			</div>
			<div class="cell">
				<div class="option productos" onclick="window.location='products.mvc';">
					<img alt="productos" src="${pageContext.request.contextPath}/img/productos-icon.png"><br />
					<span>Productos</span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="cell">
				<div class="option servicios" onclick="window.location='services.mvc';">
					<img alt="servicios" src="${pageContext.request.contextPath}/img/servicios-icon.png"><br />
					<span>Servicios</span>
				</div>
			</div>
			<div class="cell">
				<div class="option analisis" onclick="window.location='analytics.mvc';">
					<img alt="analisis" src="${pageContext.request.contextPath}/img/analisis-icon.png"><br />
					<span>Análisis</span>
				</div>
			</div>
		</div>
	</div>
</body>
</html>