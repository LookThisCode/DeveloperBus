<?php
//require_once ("library/funciones.php");
$titulo="Permisos";
$smartRendering = "100";
$columnas = array(
            //Configuraciones (con defaults): header="", filter="", sort=str, align=center, width=*, rezisable=false, type=ed
            array(
                "header" => "Usuario",
                "filter" => "#connector_text_filter",
                "align" => "left",
                "sort" => "connector"
            ),
            array(
                "header" => "Modificar permisos"
            )
        );
?>

<div id="wrapper">
    <h1 style="margin-bottom: 20px;"><?php echo $titulo; ?></h1>

    <div id="grid" style="overflow: hidden; height:500px; border: 1px solid #313131;"></div>
</div>

<script>
    function onLoadDo() {
        myGrid = new dhtmlXGridObject("grid");
        myGrid.setImagePath("codebase/imgs/");
        myGrid.setHeader("<?php echo showMenuOption($columnas, "header"); ?>");
        myGrid.attachHeader("<?php echo showMenuOption($columnas, "filter"); ?>");
        myGrid.setColSorting("<?php echo showMenuOption($columnas, "sort"); ?>");
        myGrid.setColAlign("<?php echo showMenuOption($columnas, "align"); ?>");
        myGrid.setInitWidths("<?php echo showMenuOption($columnas, "width"); ?>");
        myGrid.enableResizing("<?php echo showMenuOption($columnas, "rezisable"); ?>");
        myGrid.setColTypes("<?php echo showMenuOption($columnas, "type"); ?>");
        myGrid.setSkin("sbdark");
        myGrid.setEditable(false);
        myGrid.init();
        myGrid.load('<?php echo "views/" . $t . "/data.php"; ?>');
        myGrid.enableSmartRendering(true, <?php echo $smartRendering; ?>);
    }
</script>