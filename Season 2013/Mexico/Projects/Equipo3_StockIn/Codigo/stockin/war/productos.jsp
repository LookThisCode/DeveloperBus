<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<html>
  <head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>
  	
  <body>
<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
      pageContext.setAttribute("user", user);
%>



  <form action="/catalogos" method="post">
  	<div align="right">
	<p>Hello, ${fn:escapeXml(user.nickname)}! (You can
	<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
	<%
	    } else {
	%>
	<p>Hello!
	<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
	</p>
	</div>
	<%
	    }
	%>
  	<img href="imgs/catalogoIcon.jpg" alt="catalogo"/>
  	<h2>Cat&aacute;logos</h2>
    <div align="left">Categor&iacute;a <input type="text" name="categoria" size="45"></div>
    <div align="left">Descripci&oacute;n de la categor&iacute;a <textarea name="content" rows="3" cols="60"></textarea></div>
    <br/>
    <div align ="center"><input type="submit" value="Agregar"/></div>
  </form>

  </body>
</html>