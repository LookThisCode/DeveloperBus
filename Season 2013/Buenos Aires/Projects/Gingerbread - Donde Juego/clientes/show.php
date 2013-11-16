<?php if (substr_count($_SERVER['HTTP_ACCEPT_ENCODING'], 'gzip')) ob_start("ob_gzhandler"); else ob_start(); ?>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd"> 

<?php 
session_start();
$to="show.php?t=productos"; //redireccion cuando $t no coincide con ninguna seccion
$ruta="pages/show/show_"; 
 ?>

<html lang="es">
	<head>
		<link href="../imagenes/icon.png" rel="shortcut icon">
		<title>Donde Juego? - Clientes</title>
		<link rel="stylesheet" href="style.css" type="text/css" media="screen" />
		<link rel="stylesheet" type="text/css" href="codebase/dhtmlxgrid.css">
		<script src="codebase/dhtmlxcommon.js" type="text/javascript"></script>
		<script src="codebase/dhtmlxgrid.js" type="text/javascript"></script>
		<script src="codebase/dhtmlxgridcell.js" type="text/javascript"></script>
		<script src="codebase/ext/dhtmlxgrid_filter.js" type="text/javascript"></script>
		<script src="codebase/ext/dhtmlxgrid_srnd.js"></script>
		<script src="codebase/connector/connector.js"></script>
		<script src="library/funciones.js"></script>
	</head>

	<body onload="onLoadDo();">
		<?php 
		include('library/funciones.php');
		
		include('headder.php');
		?>		
	</body>
</html>
