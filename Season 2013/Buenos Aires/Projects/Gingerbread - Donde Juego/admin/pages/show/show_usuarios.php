

<div id="wrapper">
	<h1 style="margin-bottom: 20px;">Usuarios</h1>
	<?php 
		if (ISSET($_POST['deleteID'])) {
			$postId = $_POST['deleteID'];
			if (returnIdUsuario($_SESSION['userActual']) != $postId){
				$respuesta = borrarUsuario($postId);
				if ($respuesta != "ok"){
					echo '<ul class="log" id="log"><li>'. $respuesta .'</li></ul>';
				}
			}
			else
				echo '<ul class="log" id="log"><li>No se puede eliminar a si mismo.</li></ul>';
		}
	?>
	<input type="button" value="Agregar" style="margin-bottom: 20px; margin-top:0;" onclick="window.location = 'upload.php?t=usuarios'"/>
	<div id="grid" style="overflow: hidden; height:500px; border: 1px solid #313131;"></div>
</div>

<script>
function onLoadDo(){
	myGrid = new dhtmlXGridObject("grid");
	myGrid.setImagePath("codebase/imgs/");
	myGrid.setHeader("Usuario,Acciones");
	myGrid.attachHeader("#connector_text_filter,");
	myGrid.setColSorting("connector,");
	myGrid.setColAlign("left,center");
	myGrid.setInitWidths("*,*");
	myGrid.enableResizing("false,false");
	myGrid.setColTypes("ed,ed");
	myGrid.setSkin("sbdark");
	myGrid.setEditable(false);
	myGrid.init();
	myGrid.load("pages/data/data_usuarios.php");
}
</script>