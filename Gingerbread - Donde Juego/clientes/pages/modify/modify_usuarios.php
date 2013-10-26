


<div id="wrapper" style="width:500px;">
	<h1 style="margin-bottom: 20px;">Modificar Usuario</h1>
	<?php 
		include('../conexion.php');		
		$to="show.php?t=usuarios";
		if( isset($_GET['id']) && $_GET['id'] != "" && is_numeric($_GET['id'])) {
			$id = $_GET['id'];				
		}
		else {
			redireccionar($to);
		}
		
		$query = "SELECT * FROM usuarios WHERE borrado=0 AND id=". $id;
		$result = mysql_query($query, $conexion);
		$row = mysql_fetch_assoc($result);
		if( $row == false ){
			redireccionar($to);
		}
		$usuario = $row['usuario'];
		$old_password = $row['password'];
		$salt = $row['salt'];
		
			if (ISSET ($_POST['password'])) {
				$nuevoPassword = filter_var($_POST['password'],FILTER_SANITIZE_STRING);
		
				if ($nuevoPassword != "" && strlen($nuevoPassword) <= 50 && strlen($nuevoPassword) > 7 ){
					$nuevoPassword = encriptar((filter_var($_POST['password'],FILTER_SANITIZE_STRING)),$salt);
					$query = "UPDATE usuarios SET password='". $nuevoPassword ."' WHERE id=".$id;
					mysql_query($query, $conexion);
					
					if (mysql_error() == ''){
						echo '<ul id="log" > <li>Se ha cargado los datos correctamente</li></ul>';
						guardarActividad($_SESSION['userActual'], "El usuario ha modificado el password de: \"$usuario\" ID: $id.");
						redireccionar($to);
					}
					else {
						echo '<ul id="log" class="log"> <li>'. mysql_error().'</li></ul>';
					}
				}
			}
			else {
				echo '<ul id="log"></ul>';				
			}
	?>

	
	<form name="form" method="post" action="modify.php?t=usuarios&id=<?php echo $id?>" onsubmit="return formValidationUsuarios();" enctype="multipart/form-data" >
		<ul>
			<li><label for="menu">Usuario</label></li>
			<li><input type="text" name="usuario" size="35" maxlength="30" disabled value="<?php echo $usuario; ?>"/></li>
			<li><label for="menu">Contraseña</label></li>
			<li><input type="password" name="oldPassword" size="35" maxlength="30" disabled value="RELLENO"/></li>
			<li><label for="menu">Nueva Contraseña</label></li>
			<li><input type="password" name="password" size="35" maxlength="30" value=""/></li>
			<input type="button" name="Cancelar" value="Cancelar" class="submit" onclick="window.location = '<?php echo $to; ?>'" />
			<input type="submit" class="submit" name="submit" value="Guardar" />
		</ul>
	</form>
</div>