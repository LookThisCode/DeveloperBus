<?php
	$to="show.php?t=permisos";
	if  (isset($_GET['id']) && $_GET['id'] != "" && is_numeric($_GET['id']))
	{
		$idusuario = $_GET['id']; 
	}else
	{			
		redireccionar($to);
	}

	$nombreUsuario = returnNombreUsuario($idusuario); //recibo nombre del usuario

	if ($nombreUsuario == "" || $nombreUsuario == false){
		redireccionar($to);
	}// por si el id recibido en el GET "id" no es valido.
?>

<div id="wrapper" style="width:500px;">
	<h1 style="margin-bottom: 20px;">Modificar Permisos: <?php echo $nombreUsuario; ?></h1>
	
	<?php
		//EMPIEZA EL TRABAJO PARA BUSCAR LOS DATOS Y COMPLETAR LOS CUADROS

		$query = "select secciones.nombre as seccion from permisos INNER JOIN secciones ON permisos.seccion = secciones.id where permisos.usuario = $idusuario";//
		$rs = mysql_query($query, $conexion); //recibo las secciones PERMITIDAS por este usuario
		$cantSeccionesPermitidas=0;
		while($row = mysql_fetch_array($rs)){
			$seccionesPermitidas[$row["seccion"]] = 1; //guardo en un array las secciones permitidas del usuario. Lo guardo como map array['key'] = value.
			$cantSeccionesPermitidas++; //guardo la cantidad de secciones PERMITIDAS por este usuario
		}
			
		$query = "select secciones.nombre as seccion, secciones.id  as id, secciones.nombreVisible from secciones where secciones.visible=1";
		$rs = mysql_query($query, $conexion); //recibo todas las secciones existentes
		
		$permitidas=""; //guardo string de "<li>" con secciones permitidas
		$denegadas="";//guardo string de "<li>" con secciones denegadas
		$cantTodasSecciones=0; //contador que aumenta con las secciones totales
		
		while($row = mysql_fetch_array($rs)){ 
			$nombreVisible = $row["nombreVisible"];
			$nombreSeccion = $row["seccion"];
			$idSeccion = $row["id"]; 
			$todasSecciones[$nombreSeccion]= $idSeccion; //guarda arrat con TODAS las secciones del admin con sus ID. $nombreSeccion => $idSeccion
			$cantTodasSecciones++;
			
			if ( isset($seccionesPermitidas[$nombreSeccion]) ){
				$permitidas=$permitidas."<li value='1' id='$nombreSeccion'>$nombreVisible</li>";
			}else{
				$denegadas=$denegadas."<li value='0' id='$nombreSeccion'>$nombreVisible</li>";
			}
		}

	//AGARRO LOS POST PARA ACTUALIZAR (SI ES QUE HAY)
		
		if ( isPOST("idUsuario" ) ) {
			
			$query = "DELETE FROM permisos WHERE usuario='$idusuario'"; //Borro todos los permisos anteriores del usuario
			mysql_query($query, $conexion);
			
			if (mysql_error() == ''){
				foreach ($todasSecciones as $key => $value ) {
					if ( isPOST($key) && $_POST[$key] == 1 || ($key == "permisos" && !mysql_hayOtrosConPermisos($value,1)) )  { 		//agrego permiso por permiso
						$query = "INSERT INTO permisos (usuario, seccion) VALUES ('$idusuario', '$value')";
						mysql_query($query, $conexion);
						
						if (mysql_error() == ''){
							
						}
						else {
							echo '<ul id="log"> <li>'. mysql_error().'</li></ul>';
						}
					}		
				}
				guardarActividad($_SESSION['userActual'], "El usuario ha modificado los permisos del usuario: \"$nombreUsuario\" ID: $idusuario.");
				redireccionar($to);
			}
			else {
				echo '<ul id="log"> <li>'. mysql_error().'</li></ul>';
			}
		}
			
	?>
	
	<!-- scripts drag --> 	
	<script src="library/funciones.js"></script>
	<script language="javascript" src="library/js-draggable/jquery-1.3.1.min.js"></script>
	<script language="javascript" src="library/js-draggable/jquery-ui-personalized-1.6rc6.min.js"></script>
	<script language="javascript">
		$(document).ready(function(){
			 // defino listas como variables
			var $listaPermitidos = $('#listaPermitidos'), $listaDenegados = $('#listaDenegados');
			// activo draggable a la primera lista
			$('li',$listaPermitidos).draggable({
				revert: 'invalid',      
				helper: 'clone',        
				cursor: 'move'
			});
			   // asigno droppable en la listaPermitidos hacia la listaDenegados
			$listaPermitidos.droppable({
				accept: '#listaDenegados li',
				drop: function(ev, ui) {
							   // Al hacer drop se borra el elemento
					deletelistaDenegados(ui.draggable);
				}
			});
			// Asigno draggable a la listaDenegados
			$('li',$listaDenegados).draggable({
				revert: 'invalid',
				helper: 'clone',    
				cursor: 'move'
			});
			   // Genero droppable para la segunda lista
			$listaDenegados.droppable({
				accept: '#listaPermitidos > li',
				drop: function(ev, ui) {
					deletelistaPermitidos(ui.draggable);     
				}
			});
			// Genero el borrado de items con el evento drop
			function deletelistaPermitidos($item) {
				$item.fadeOut(function() {
							 // Agrego el item dropeado y lo hago aparecer
					$($item).appendTo($listaDenegados).fadeIn();
					$($item).val("0");
				});
				$item.fadeIn();
			}
			function deletelistaDenegados($item) {
				$item.fadeOut(function() {          
								/// Agrego el item dropeado y lo hago aparecer
					$item.appendTo($listaPermitidos).fadeIn();
					$($item).val("1");
				});
			}
		});
	</script>
	<!-- /scripts drag --> 

	<div class="divListas">
		<ul id="listaPermitidos">
			<p>Permitido</p>
			<?php echo $permitidas; ?>
		</ul>
		<ul id="listaDenegados">
			<p>Denegado</p>
			<?php echo $denegadas; ?>
		</ul>
	</div>

	<div>
		<input class="grabarPermisos" type="button" name="Cancelar" value="Cancelar" class="submit" onclick="window.location = '<?php echo $to; ?>'" />
		<input class="grabarPermisos" type="button" value="Guardar" onclick="JSPost('modify.php?t=permisos&id=<?php echo $idusuario; ?>', <?php echo "{idUsuario:".$idusuario;
			foreach ( $todasSecciones as $seccion => $value) {
				echo ", ". $seccion .": document.getElementById('". $seccion ."').value";
			}
			echo "} );";
		?>
		">
	</div>
</div>

</div>