<?php
	$titulo="Colores";
	$seccion="colores";
?>

<div id="wrapper">
	<h1 style="margin-bottom: 20px;"><?php echo $titulo ?></h1>
	<?php 
		if (ISSET($_POST['deleteID'])) {
			$postId = $_POST['deleteID'];
			$respuesta = borrarColor($postId);
			if ($respuesta != "ok"){
				echo '<ul class="log" id="log"><li>'. $respuesta .'</li></ul>';
			}
		}
	?>
	<input type="button" value="Agregar" style="margin-bottom: 20px; margin-top:0;" onclick="window.location = 'upload.php?t=<?php echo $seccion ?>'"/>
	<div id="grid" style="overflow: hidden; height:500px; border: 1px solid #313131;"></div>
</div>

<script>
function onLoadDo(){
	myGrid = new dhtmlXGridObject("grid");
	myGrid.setImagePath("codebase/imgs/");
	myGrid.setHeader("Nombre, Codigo, Color,Acciones");
	myGrid.attachHeader("#connector_text_filter,#connector_text_filter,,");
	myGrid.setColSorting("connector,connector,,");
	myGrid.setColAlign("left,center,center,center");
	myGrid.setInitWidths("*,*,*,70");
	myGrid.enableResizing("false,false,false,false");
	myGrid.setColTypes("ed,ed,ed,ed");
	myGrid.setSkin("sbdark");
	myGrid.setEditable(false);
	myGrid.init();
	myGrid.load("pages/data/data_<?php echo $seccion ?>.php");
	myGrid.enableSmartRendering(true,20);
}
</script>