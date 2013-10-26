

<div id="wrapper" style="width:500px;">
	<h1 style="margin-bottom: 20px;">Nuevo Usuario</h1>
	<?php 
		include('../conexion.php');		
		$to="show.php?t=usuarios";
		if( isset($_POST['usuario'])) {
			$usuario = filter_var($_POST['usuario'],FILTER_SANITIZE_STRING);
			$salt = randomString(10);
			$password = encriptar((filter_var($_POST['password'],FILTER_SANITIZE_STRING)),$salt);
			if ( mysql_cantidad("usuarios", "usuario = '$usuario' AND borrado = 0") == 0){
				if ( $usuario != "" && $password != "" && strlen($password) >= 8 ) {
					$query = "INSERT INTO usuarios (usuario, password, salt, borrado) VALUES ( '". $usuario ."', '". $password ."', '". $salt ."', 0)";
					mysql_query($query, $conexion);
					$id = mysql_insert_id();
					if (mysql_error() == ''){
						echo '<ul id="log"> <li>Se ha cargado los datos correctamente</li></ul>';		
						guardarActividad($_SESSION['userActual'], "El usuario ha creado el usuario: \"$usuario\" ID: $id.");
						redireccionar("modify.php?t=permisos&id=".$id );
					}
					else {
						echo '<ul id="log" class="log"> <li>'. mysql_error().'</li></ul>';
					}
				}
				else {
					$password = "";
					echo '<ul id="log" class="log"><li>Hubo problemas al completar el formulario. Intente otra vez.</li></ul>';
				}
			}
			else {
				$password = "";
				echo '<ul id="log" class="log"><li>Ya existe ese nombre de usuario. Elija otro.</li></ul>';
			}
		}
		else {
			echo '<ul id="log"></ul>';				
		}
	?>

	
	<form name="form" method="post" action="upload.php?t=usuarios" onsubmit="return formValidationUsuarios();" enctype="multipart/form-data" >
		<ul>
			<li><label for="menu">Usuario</label></li>
			<li><input type="text" name="usuario" size="35" maxlength="50" value=""/></li>
			<li><label for="menu">Contraseña</label></li>
			<li><input type="password" name="password" size="35" maxlength="50" value=""/></li>
			<input type="button" name="Cancelar" value="Cancelar" class="submit" onclick="window.location = '<?php echo $to; ?>'" />
			<input type="submit" class="submit" name="submit" value="Agregar" />
		</ul>
	</form>

</div>