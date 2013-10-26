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
		<title>Donde Juego? - Admin</title>
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
