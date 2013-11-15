

<div id="wrapper">
	<h1 style="margin-bottom: 20px;">Novedades</h1>
	<?php 
		if (ISSET($_POST['deleteID'])) {
			$postId = $_POST['deleteID'];
			$respuesta = borrarNovedad($postId);
			if ($respuesta != "ok"){
				echo '<ul class="log" id="log"><li>'. $respuesta .'</li></ul>';
			}
			redireccionar($_SERVER['REQUEST_URI']);
		}
		/*
		if (ISSET($_POST['subir'])) {
			$id = $_POST['subir'];
		
			$respuesta = mysql_subirUnoSelectivo("novedades", "orderBy", "id=$id", "borrado=0");
			
			if ($respuesta != "ok"){
				echo '<ul class="log" id="log"><li>'. $respuesta .'</li></ul>';
			}
			redireccionar($_SERVER['REQUEST_URI']);
		}
		
		if (ISSET($_POST['bajar'])) {
			$id = $_POST['bajar'];
		
			$respuesta = mysql_bajarUnoSelectivo("novedades", "orderBy", "id=$id", "borrado=0");
			
			if ($respuesta != "ok"){
				echo '<ul class="log" id="log"><li>'. $respuesta .'</li></ul>';
			}
			redireccionar($_SERVER['REQUEST_URI']);
		}*/
	?>
	<input type="button" value="Agregar" style="margin-bottom: 20px; margin-top:0;" onclick="window.location = 'upload.php?t=novedades'"/>
	<div id="grid" style="overflow: hidden; height:500px; border: 1px solid #313131;"></div>
</div>

<script>
function onLoadDo(){
	myGrid = new dhtmlXGridObject("grid");
	myGrid.setImagePath("codebase/imgs/");
	myGrid.setHeader("Nombre,Descripción,Imagen,Visible,Acciones");
	myGrid.attachHeader("#connector_text_filter,#connector_text_filter,,,");
	myGrid.setColSorting("str,str,,int,");
	myGrid.setColAlign("left,center,center,center,center");
	myGrid.setInitWidths("*,*,*,48,100");
	myGrid.enableResizing("false,false,false,false,false");
	myGrid.setColTypes("ed,ed,ed,ch,ed");
	myGrid.setSkin("sbdark");
	myGrid.setEditable(false);
	myGrid.init();
	myGrid.load("pages/data/data_novedades.php");
	myGrid.enableSmartRendering(true,20);
}
</script>