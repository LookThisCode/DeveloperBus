<?php if (substr_count($_SERVER['HTTP_ACCEPT_ENCODING'], 'gzip')) ob_start("ob_gzhandler"); else ob_start(); ?>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd"> 

<?php
session_start();
include('../conexion.php');
include('library/funciones.php');
include('config.php');

$a="";										//
if (isset($_GET['a'])) { $a=$_GET['a']; }	//Me fijo si fue direccionado desde un "cerrar sesion" (si GET => a=logout, lo fue)
if ($a == "logout") { guardarActividad($_SESSION['userActual'], "El usuario se ha desconectado."); session_destroy(); redireccionar($redireccion);} //

/*VALIDO SI YA HAY USUARIO LOGUEADO*/
if (isset($_SESSION['userActual'])){ redireccionar($redireccionLog); }

/*VALIDO datos de usuario*/
$valido = 2; //sin ingreso de datos
if (isset($_REQUEST['submit']) ) {
	$valido = 0;
	if($_REQUEST['userRequest'] != "" && $_REQUEST['passwordRequest'] != "") {
		$valido = 1;
		$query = "select usuario, password, salt from  usuarios where usuario = '" . reemplazar($_REQUEST['userRequest']) . "'";
		$rs = mysql_query($query, $conexion) or die ("Error 1");
		if (mysql_fetch_row($rs)) $valido = 1; // 1 si conexion exitosa
		$rs = mysql_query($query, $conexion) or die ("Error 2");
		while($resul = mysql_fetch_array($rs)) {
			if (reemplazar(encriptar($_REQUEST['passwordRequest'],$resul["salt"])) == reemplazar($resul["password"]) ) { //Comparo password
				guardarActividad($resul["usuario"], "El usuario ha iniciado sesi&oacute;n.");
				$_SESSION['userActual']= $resul["usuario"]; //guardo en el session la id del user actual
				redireccionar($redireccionLog);
			}
		}
	}
}
?>

<html>
	<head>
		<link href="../imagenes/icon.png" rel="shortcut icon">
		<title><?php echo $NombreEmpresa; ?> - Admin</title>
		<link href="style.css" type="text/css" rel="stylesheet">
		<script src="library/funciones.js"></script>
		<script src="library/validation_users.js"></script>
	</head>
	<body onload="document.formLogin.userRequest.focus()">

		<div id="wrapper" style="width:425px; margin-top: 40px;">
		<div class="tituloForm" id="div_done"><p style="MARGIN: 5px"><?php echo $NombreEmpresa; ?></p></div>

		<?php
		if ($valido == 2)
			{
			?>
			<div class="bordeAzul" id="div_done" >
				<img style="float: left; margin-right: 10px" height="32" src="images/info.png" width="32"> 
				<p style="margin: 5px">Bienvenido al administrador de contenidos de <?php echo $NombreEmpresa; ?> . <br/>Ingrese sus datos de usuario para acceder al sistema. </p>
			</div>
			<?php
			}
		elseif ($valido == 1)
			{
			?>
			<div class="bordeAmarillo" id="div_done">
				<img style="float: left; margin-right: 10px" height="32" src="images/alert.png" width="32"> 
				<p style="margin: 5px">Contraseña incorrecta. <br/>Intente nuevamente para ingresar al sistema.</p>
			</div>
			<?php
			}
		else
			{
			?>
			<div class="bordeAmarillo" id="div_done" >
				<img style="float: left; margin-right: 10px" height="32" src="images/alert.png" width="32"> 
				<p style="margin: 5px">No se han completado todos los campos. <br/>Complete todos los campos para acceder al sistema.</p>
			</div>
			<?php
			}
		?>
		
		<form id="formLogin" name="formLogin" method="post" style="width: 400px;">
			<ul>
				<li><label style="width: 70px;" for="nombre">Usuario </label></li>
				<li><input id="userRequest" style="padding: 2px; width: 250px;border: 1px solid #cccccc;" name="userRequest" value="<?php if (isset($_REQUEST['userRequest'])) echo $_REQUEST['userRequest']?>"></li>
				<li><label style="width: 70px;" for="nombre">Password </label></li>
				<li><input id="passwordRequest"  style="padding: 2px; width: 250px;border: 1px solid #cccccc;" type="password" name="passwordRequest"></li>
				<INPUT class="submit" type="submit" value="Entrar" name="submit">
			</ul>
		</form>
			
		</div>
	</body>

<?php
mysql_close($conexion);
?>
</html>