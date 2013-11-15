

<div id="wrapper">
	<h1 style="margin-bottom: 20px;">Actividades</h1>
	<?php 
		/* NO ES EDITABLE EL DE ACTIVIDADES
		if (ISSET($_POST['deleteID'])) {
			$postId = $_POST['deleteID'];
			$respuesta = borrarMenu($postId);
			if ($respuesta != "ok"){
				echo '<ul class="log" id="log"><li>'. $respuesta .'</li></ul>';
			}
		}
		*/
	?>
	<div id="grid" style="overflow: hidden; height:500px; border: 1px solid #313131;"></div>
</div>

<script>
function onLoadDo(){
	myGrid = new dhtmlXGridObject("grid");
	myGrid.setImagePath("codebase/imgs/");
	myGrid.setHeader("Descripción,Usuario,Fecha");
	myGrid.attachHeader("#connector_text_filter,#connector_select_filter,#connector_text_filter");
	myGrid.setColSorting("str,str,str");
	myGrid.setColAlign("left,center,center");
	myGrid.setInitWidths("*,150,150");
	myGrid.enableResizing("false,false,false");
	myGrid.setColTypes("ed,ed,ed");
	myGrid.setSkin("sbdark");
	myGrid.setEditable(false);
	myGrid.init();
	myGrid.load("pages/data/data_actividades.php");
	myGrid.enableSmartRendering(true,100);
}
</script>