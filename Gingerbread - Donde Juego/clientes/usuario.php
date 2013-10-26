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
		<br>
		<DIV style="margin:80px;float:left;">
		<p style="font:normal 18px Arial,Helvetica,sans-serif;">Reservas activas:</p>
		<p style="font:normal 14px Arial,Helvetica,sans-serif;">	15/11/13 13:30 hs - SERRANO CORNER - AV. CORRIENTES 2293 <B>RESERVADO</B> - <a href="#" style="color:red;">CANCELAR RESERVA</a>
			<BR>
			05/11/13  23:00 hs - FUTBOL MARANGONI - Juan Jose Cruz 394 <B>PAGADO</B>  - <a href="#" style="color:red;">CANCELAR RESERVA</a>
		</p>
		<p style="font:normal 18px Arial,Helvetica,sans-serif;">Historial de reservas:</p>
		<p style="font:normal 14px Arial,Helvetica,sans-serif;">
			02/12/2013 12:00 hs - MAREANO MEREDEZ 323 - <a style="color:green;">ASISTIDO</A><BR>
			22/12/2013 12:00 hs - MAREANO MEREDEZ 323 - <a style="color:green;">ASISTIDO</A><BR>
		
			22/12/2013 12:00 hs - MAREANO MEREDEZ 323 - <a style="color:RED;">AUSENTE</A><BR>
			22/12/2013 12:00 hs - MAREANO MEREDEZ 323 - <a style="color:RED;">AUSENTE</A><BR>

			18/09/2013 12:00 hs - MAREANO MEREDEZ 323 - <a style="color:green;">ASISTIDO</A><BR>
			15/04/2013 12:00 hs - MAREANO MEREDEZ 323 - <a style="color:green;">ASISTIDO</A><BR>
			
			05/11/2013 12:00 hs - MAREANO MEREDEZ 323 - <a style="color:RED;">ASUSENTE</A><BR>
			22/12/2013 12:00 hs - MAREANO MEREDEZ 323 - <a style="color:green;">ASISTIDO</A><BR>
			02/12/2013 12:00 hs - MAREANO MEREDEZ 323 - <a style="color:green;">ASISTIDO</A>			
		</p>
		</div>
		<div style="margin-top:80px"><img src="images/ultimas-reservas.png"></div>
	</body>
</html>
