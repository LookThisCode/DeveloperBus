<?php
	include("config.php");
	include("../conexion.php");
	if ( !isset($_SESSION['userActual']) ){redireccionar($redireccion);} //VALIDO QUE ESTE LOGUEADO EL USUARIO
	if ( !tienePermisosParaVer($t,$_SESSION['userActual']) ){				//VALIDO QUE TENGA PERMISOS PARA VER LA SECCION
		if ( $t == $tablaRedireccion ) {						//Si justo no tiene permisos para la tabla que va predefinida, lo mando a otra. Si no tiene ninguna seccion habilitada, le cierro la sesion.
			$redireccionLog = tablaQueTengaPermiso($_SESSION['userActual']);
		}
		redireccionar($redireccionLog);
	} 
?>
<div id="headder">
	<div class="botonHeadder sigmaAdmin"><h2><img src="images/logo.png"></h2></div>
	
	<?php if(tieneAlgunaSeccion($_SESSION['userActual'],1) ) { ?>
	<div class="botonHeadder menuAcordion" onmouseover="showContent(this);" onmouseout="hideContent(this);">
		<a id="contenedorHeadder">Administración</a>
		<ul id="contenidoHeadder" class="contenidoAcordion">
		<?php
		$idUsuario = returnIdUsuario($_SESSION['userActual']);
		
		$query = "SELECT secciones.nombre as seccion, secciones.nombreVisible FROM permisos 
				INNER JOIN secciones ON permisos.seccion=secciones.id 
				WHERE permisos.usuario = $idUsuario AND secciones.administrativo=1 AND secciones.visible=1 ORDER BY seccion";
		$rs = mysql_query($query, $conexion);
		while ($row = mysql_fetch_array($rs))
		{
			echo "<li><a href='show.php?t=".$row["seccion"]."'><span class='miniImagen img".$row["seccion"]."'></span>".$row["nombreVisible"]."</a></li>";
		}
		?>	
		</ul>
	</div>
	
	<?php }
	if(tieneAlgunaSeccion($_SESSION['userActual'],0) ) { ?> 
	<div class="botonHeadder menuAcordion" onmouseover="showContent(this);" onmouseout="hideContent(this);">
		<a id="contenedorHeadder">Secciones</a>
		<ul id="contenidoHeadder" class="contenidoAcordion">
		<?php
		$idUsuario = returnIdUsuario($_SESSION['userActual']);
		
		$query = "select secciones.nombre as seccion, secciones.nombreVisible from permisos 
				INNER JOIN secciones ON permisos.seccion=secciones.id 
				where permisos.usuario = $idUsuario AND secciones.administrativo=0 AND secciones.visible=1 ORDER BY seccion";
		$rs = mysql_query($query, $conexion);
		while ($row = mysql_fetch_array($rs))
		{
			echo "<li><a href='show.php?t=".$row["seccion"]."'><span class='miniImagen img".$row["seccion"]."'></span>".$row["nombreVisible"]."</a></li>";
		}
		?>	
		</ul>
	</div>
	<?php } ?>
	
	
	<div class="botonHeadder cerrarSesion"><a href="index.php?a=logout">Cerrar Sesión</a></div>
	<div class="botonHeadder bienvenidoUsuario"><p><?php echo "Bienvenido: <b>".$_SESSION['userActual']; ?></b></p></div>
</div>

<script>
	function showContent(t){
		t.childNodes[1].setAttribute("class", "fondoNegro"); 
		t.childNodes[1].setAttribute("className", "fondoNegro");
		t.childNodes[1].style.color = '#fff';
		t.childNodes[3].style.display = 'block';
	}
	
	function hideContent(t){
		t.childNodes[3].style.display = 'none';
		t.childNodes[1].style.color = '#aaa';
		t.childNodes[1].className = "";

	}

</script>