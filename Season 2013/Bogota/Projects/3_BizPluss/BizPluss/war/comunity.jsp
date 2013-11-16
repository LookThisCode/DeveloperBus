<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="Model.Company"%>
<%@ page import="Model.User"%>
<%@ page import="Model.OfyService.ofy"%>
<%@ page import="java.util.List"%>

<%
	String  userid = request.getParameter("userId");
	String  companyid = request.getParameter("companyId");
	
	String username = "";
	
	String companyname = "";
	String email = "";
	String profileUrl  = "";
	String imageUrl = "";
	String description = "";
	String tagline = "";
	List<String> reconocimientos ;
	
	
	User user = ofy().load().type(User.class).id(Long.parseLong(userId)).get();
	if (user != null) {
	  username = user. getGoogleDisplayName();
	}
	
	
	Company comp = ofy().load().type(Company.class).id(Long.parseLong(companyId)).get();
	if (company != null) {
		companyname = company.getGoogleDisplayName();
		profileUrl = company.getGooglePublicProfileUrl();
	  	imageUrl = company.getThumbnailUrl();
	  	email = user.getEmail();
	  	reconocimientos = company.getBraggingRights();
	  	description = company.getDescription();
	  	tagline = company.getTagline();	 
	  	numberPlus = company.getNumberPlus();
	  	numberCircles = company.getNumberCircles();	  	 	
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
		  		<li>
		  			<a><%= companyname %><img class="icon" /></a>
		  		</li>
		  	</ul>
		  </nav>
	  </header>
	  <div id="wrapper">
		  	<div class="profile">
		  		<a href="<%= profileURL %>"><h3><%= companyname %></h3>
		  		<img href="<%= imageUrl %>" class="profile_img"/></a>
		  		  		
		  		
		  		<ul class="form_fields">
		  			<li><label>Descripción: </label><%= description %></li>
		  			<li><label>Correo Electronico: </label><%= email %></li>
		  			<li><label>Tagline: </label><%= tagline %></li>
		  			<li><label>Number of Plus: </label><%= numberPlus %></li>
		  			<li><label>Number of Circles: </label><%= numberCircles %></li>
		  		</ul>
		  		
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