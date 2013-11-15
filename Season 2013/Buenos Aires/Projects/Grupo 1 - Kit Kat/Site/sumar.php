<!DOCTYPE html>
<!--[if IE 8]> 				 <html class="no-js lt-ie9" lang="en" > <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="en" > <!--<![endif]-->

<head>
	<meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1.0">
  <title>Viaje Ecológico | Sumar un viaje</title>

  
  <link rel="stylesheet" href="css/foundation.css">
  <link rel="stylesheet" href="css/style.css">
  <link rel="stylesheet" href="css/responsive.css">
  <link rel="stylesheet" href="css/font-awesome.css">
  <link href='http://fonts.googleapis.com/css?family=GFS+Didot' rel='stylesheet' type='text/css'>
  <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
  
  <link rel="shortcut icon" href="ico/favicon.ico">
  <link rel="apple-touch-icon" sizes="144x144" href="ico/apple-touch-icon-144.png">
  <link rel="apple-touch-icon" sizes="114x114" href="ico/apple-touch-icon-114.png">
  <link rel="apple-touch-icon" sizes="72x72" href="ico/apple-touch-icon-72.png">
  <link rel="apple-touch-icon" href="ico/apple-touch-icon-57.png">

<script>
  var toggle = function() {
  var mydiv = document.getElementById('tutoriales');
  if (mydiv.style.display === 'block' || mydiv.style.display === '')
    mydiv.style.display = 'none';
  else
    mydiv.style.display = 'block'
  }
</script>
  


    <style>
      html, body, #map-canvas {
        height: 100%;
        margin: 0px;
        padding: 0px
      }
    </style>
	
	
</head>
<body style="border-top-color: #CEFF00;border-top-style: solid;border-top-width:5px;">
<div id="main-container">

<section id="services" class="pad-small">

	<div class="row">
		<div class="twelve columns" style="text-align: center;margin-bottom: 7%;">
			<a href="index.html"><img src="img/ve_logo.svg" alt="Viaje Ecológico">
			<h1 style="color: #719904;text-shadow: 1px 1px #B2DB05;">VIAJE ECOLÓGICO</h1></a>
					<div id="copy-text" style="line-height: 15px;">Carpooling con conciencia ecológica, de verdad <div class="icon icon-leaf"></div>
					</div>
		</div>
		</div>
	
	
<div class="row" style="height: 1%;display: block;"></div>	
	
	<div class="row service-row">
		
		<div class="large-10 large-centered columns " style="margin-top: 2%;">
		
			<div class="large-12 columns  ">
				
				<h5>SUMAR UN VIAJE</h5><br />
				<p id="copy-text">Ingresá tu viaje al directorio.</p>
				<br />
				
				    <form action="index.html" method="post">
				    
				    
				    <div id="origendestino" style="width: 500px;">
				    	<div id="origen" style="float: left; width: 240px;">
				    		<p><b>Origen</b> <input type="text" name="origen" /></p>
				    	</div>
				    		
				    	<div id="destino" style="float: right; width: 240px;">
				    		<p><b>Destino:</b> <input type="text" name="destino" /></p>
				    	</div>
				    </div>
				    
				    
				    <br /><br />
				    
				    
					
					<div id="fechahora">
						<div id="fecha" style="width: 200px;">
							<p><b>Fecha:</b></p>
							<input type="text" name="dia" style="width: 40px; float: left;margin-right: 5px;"/>
								<input type="text" name="año" style="width: 40px;  float: right;margin-right: 5px;"/>
								<input type="text" name="mes" style="display: inline-block; margin:0 auto;width:100px;margin-right: 5px;"/>
								<div style="clear:both;"></div>
						</div>
					</div>
						
						<br />
						
						
						<div id="horas" style="width: 100px;">
							<div id="hora">
								<p><b>Hora:</b></p>
								<input type="text" name="horas" style="width: 40px;float: left;"/>
							</div>
							
							
							<div id="hora">
								<input type="text" name="minutos" style="width: 40px;float: right;"/>
							</div>
						
						</div>
						
						
					
								 <?php
								 	 $fecha = $_POST["año"]."-".$_POST["mes"]."-".$_POST["dia"];
								 	$hora = $_POST["horas"].":".$_POST["minutos"];
								 	$destino = $_POST["destino"];
								 	$origen = $_POST["origen"];
							
									mysql_connect("localhost","root","");
									mysql_select_db("viajes");
									$resul = mysql_query("INSERT INTO viaje (Destino,Fecha,Hora,Pasajeros,Salida) VALUES ('".$destino."','".$fecha."','".$hora."','".$origen."')");
								?>
								
					 
				
				
				
				

					
					<hr>
				
				
				<h5>DATOS DEL CHOFER</h5><br />
				<p id="copy-text">Ingresá tus datos de contacto.</p>
				<br />
				
				
				<div id="nombreapellido" style="width: 500px;">
					<div id="nombre" style="float: left; width: 240px;">
						<p><b>Nombre: </b> <input type="text" name="nombre" /></p>
					</div>
						
					<div id="apellido" style="float: right; width: 240px;">
						<p><b>Apellido:</b> <input type="text" name="apellido" /></p>
					</div>
				</div>
				
				
				<br /><br /><br /><br />
				
				<div id="telefonomail" style="width: 500px;">
					<div id="telefono" style="float: left; width: 240px;">
						<p><b>Teléfono: </b> <input type="text" name="tel" /></p>
					</div>
						
					<div id="mail" style="float: right; width: 240px;">
						<p><b>Email:</b> <input type="text" name="email" /></p>
					</div>
				</div>
				
				
				         <p><b>Cantidad de pasajeros:</b> <input type="text" name="pasajeros" style="width: 40px;"/></p>
				
				         <?php
				                 
				                
				                $nombre = $_POST["nombre"];
				                $apellido = $_POST["apellido"];
				                $telefono = $_POST["tel"];
				                $email = $_POST["email"];
				                $pasajeros = $_POST["pasajeros"];
				
				                mysql_connect("localhost","root","");
				                mysql_select_db("viajes");
				                $resul = mysql_query("INSERT INTO chofer (Apellido,CantidadPasajeros,Email,Nombre,Telefono) VALUES ('".$apellido."','".$pasajeros."','".$email."','".$nombre."','".$telefono."')");
				        ?>
				         <p><input type="submit" /></p>
				        </form>
				
				
				
				
			</div>
		
	</div>
</div>



<section id="contact">

	

	<div id="contact-content">
	
				
		<div class="row social-row">
			<div class="large-12 columnsB text-center">
				
				<div class="btn">
					<h6>GOOGLE+</h6>
				</div>
				
				<div class="btn">
					<h6>TWITTER</h6>
				</div>
				
				<div class="btn">
					<h6>EMAIL</h6>
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="large-12 columns text-center">
				<div id="copy-text">Viaje Ecológico existe gracias a los servicios de Google <div class="icon icon-heart"></div></div>
			</div>
		</div>
	
	</div>

	

</section>




</div><!-- end of Main Container-->

  <script src="js/vendor/custom.modernizr.js"></script>
  <script src="js/vendor/jquery.js"></script> 
  <script src="js/foundation.min.js"></script>
  <script src="js/scripts.js"></script>
   <script src="js/smooth-scroll.js"></script>

  
  <script>
    $(document).foundation();
  </script>
</body>
</html>
