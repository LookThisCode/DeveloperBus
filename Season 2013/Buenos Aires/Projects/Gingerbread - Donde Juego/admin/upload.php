<?php if (substr_count($_SERVER['HTTP_ACCEPT_ENCODING'], 'gzip')) ob_start("ob_gzhandler"); else ob_start(); ?>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd"> 

<?php 
session_start();
$to="show.php?t=productos"; //Cambiar a t=productos
$ruta="pages/upload/upload_" 
?>

<html lang="es">
	<head>
		<link href="../imagenes/icon.png" rel="shortcut icon">
		<title>Donde Juego? - Admin</title>
		<link rel="stylesheet" href="style.css" type="text/css" media="screen" />
		<script src="library/validation.js"></script>
				<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
    <script>
		function initialize() {
		  var mapOptions = {
			zoom: 15,
			center: new google.maps.LatLng(-34.5888889,-58.4305556),
			mapTypeId: google.maps.MapTypeId.ROADMAP
		  };
		  google.maps.Animation

		  var map = new google.maps.Map(document.getElementById('map-canvas'),
			  mapOptions);

		  var marker = new google.maps.Marker({
			position: map.getCenter(),
			map: map,
			title: 'Click to zoom',
			draggable:true
		  });

		  google.maps.event.addListener(map, 'center_changed', function() {
			// 3 seconds after the center of the map has changed, pan back to the
			// marker.
			window.setTimeout(function() {
			  map.panTo(marker.getPosition());
			}, 3000);
		  });

		  google.maps.event.addListener(marker, 'click', function() {
			map.setZoom(8);
			map.setCenter(marker.getPosition());
		  });
		  google.maps.event.addListener(marker, 'click', toggleBounce);
		}
		function toggleBounce() {

		  if (marker.getAnimation() != null) {
			marker.setAnimation(null);
		  } else {
			marker.setAnimation(google.maps.Animation.BOUNCE);
		  }
		}
		google.maps.event.addDomListener(window, 'load', initialize);

</script>
	</head>

	<body>
		<?php 
		include('library/funciones.php');
		
		if( isset($_GET['t']) && $_GET['t'] != "") 
			$t = $_GET['t'];
		else			
			redireccionar($to);
			
		include('headder.php');	
				
		$query = "select nombre as seccion, id from secciones order by id";//
		$rs = mysql_query($query, $conexion);	 //me trae todos los nombres de las secciones de la pagina
		$encontrado=0;

		while($row = mysql_fetch_array($rs)){
			if ($row["seccion"] == $t){
				include($ruta.$t.".php"); 	//por cada seccion en la pagina, la compara con $t
				$encontrado=1;
				break; 	
			}
		}
		if ($encontrado==0){;
			redireccionar($to); //si $t=ASDASASD, la manda a la default
		}
		?>
			
	</body>
</html>
