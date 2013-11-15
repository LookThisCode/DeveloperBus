<?php
	include("config.php");
	include("../conexion.php");
	if ( !isset($_SESSION['userActual']) ){redireccionar($redireccion);} //VALIDO QUE ESTE LOGUEADO EL USUARIO
?>
<div id="headder">
	<div class="botonHeadder sigmaAdmin"><h2><img src="images/logo.png"></h2></div>
	
	<?php  ?> 
	<div class="botonHeadder menuAcordion" onmouseover="showContent(this);" onmouseout="hideContent(this);">
		<a id="contenedorHeadder">Secciones</a>
		<ul id="contenidoHeadder" class="contenidoAcordion">
		<?php
			echo "<li><a href='usuario.php'><span class='miniImagen imgusuarios'></span>Mis reservas</a></li>";
		
		?>	
		</ul>
	</div>
	<?php  ?>
	
	
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