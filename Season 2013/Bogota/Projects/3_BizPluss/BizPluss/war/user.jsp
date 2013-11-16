<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="Model.User"%>
<%@ page import="Model.OfyService.ofy"%>
<%@ page import="java.util.List"%>

<%
	String  userid = request.getParameter("userId");
	
	String imageUrl = "";
	String name = "";
	String description = "";
	String email = "";
	String profileURL = "";
	
	List<String> organizaciones;
	List<String> amigos;
	
	
	User user = ofy().load().type(User.class).id(Long.parseLong(userId)).get();
	if (user != null) {
	  name = user. getGoogleDisplayName();
	  description = user.getDescription();
	  email = user.getEmail();
	  profileURL = user.getGooglePublicProfileUrl();
	  imageUrl = user.getThumbnailUrl();
	}
%>

<!DOCTYPE html>
<html>
<head>
  <% if (redirectUrl != null) { %>
  <script type="text/javascript">
    window.location.href = '<%= redirectUrl %>';
  </script>
  <% } %>
  <title><%= name %></title>
</head>
<body>
  <h1 itemprop="name"><%= name %></h1>
	  <header>
		  <nav>
		  	<div class="logo"></div>
		  	<ul>
		  		<li>
		  			<a><%= username %><img class="icon" /></a>
		  		</li>
		  	</ul>
		  </nav>
	  </header>
	  <div id="wrapper">
	  	<div class="profile">
	  		<a href="<%= profileURL %>"><h3><%= name %></h3>
	  		<img href="<%= imageUrl %>" class="user_img"/></a>
	  		
	  		<aside class="left">
	  			<%= for(organizaciones:organizacion){ %>
	  				<li><%= organizacion %></li>
	  			 <%= }%>
	  		</aside>
	  		
	  		
	  		<ul class="form_fields">
	  			<li><label>Descripción: </label><%= description %></li>
	  			<li><label>Correo Electronico: </label><%= email %></li>
	  		</ul>
	  		
	  		
	  		<aside class="right">
	  			<%= for(amigos:amigo){ %>
	  				<li><%= amigo %></li>
	  			 <%= }%>
	  		</aside>
	  	</div>
	  </div>
	  <footer>
		  <nav>
		  	<ul>
		  		<li><a href="">Acerca de</a></li>
		  		<li><a href="">Preguntas Frecuentes</a></li>
		  		<li><a href="">Terminos Legales y Condiciones</a></li>
		  	</ul>
		  </nav>
	  </footer>
</body>
</html>