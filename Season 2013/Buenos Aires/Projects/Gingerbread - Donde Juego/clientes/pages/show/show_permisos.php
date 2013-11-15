

<div id="wrapper">
	<h1 style="margin-bottom: 20px;">Permisos</h1>
	<div id="grid" style="overflow: hidden; height:500px; border: 1px solid #313131;"></div>
</div>

<script>
function onLoadDo(){
	myGrid = new dhtmlXGridObject("grid");
	myGrid.setImagePath("codebase/imgs/");
	myGrid.setHeader("Usuario,Modificar permisos");
	myGrid.attachHeader("#connector_text_filter,,,,");
	myGrid.setColSorting("connector,,int,int,");
	myGrid.setColAlign("left,center");
	myGrid.setInitWidths("*,*,75,48,70");
	myGrid.enableResizing("false,false");
	myGrid.setColTypes("ed,ed");
	myGrid.setSkin("sbdark");
	myGrid.setEditable(false);
	myGrid.init();
	myGrid.load("pages/data/data_permisos.php");
	myGrid.enableSmartRendering(true,100);
}
</script>