<!DOCTYPE html>
<html>
<head>
  <% if (redirectUrl != null) { %>
  <script type="text/javascript">
    window.location.href = '<%= redirectUrl %>';
  </script>
  <% } %>
  <title><%= name %></title>
  
  <script src="http://maps.google.com/maps?file=api&v=2&key=AIzaSyA7KFoPBut90-FymjFHvVgViONNSJfE-cw" type="text/javascript"></script>
  <script type="text/javascript" src="js/maps.js"></script>
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
	  	<div id="message">

		</div>
		<div id="map_container">
			  	
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