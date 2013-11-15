<?php if (substr_count($_SERVER['HTTP_ACCEPT_ENCODING'], 'gzip')) ob_start("ob_gzhandler"); else ob_start(); ?>
<?php 
	require_once("../../../conexion.php");
	include("../../library/funciones.php");
	session_start();
	if ( !isset($_SESSION['userActual']) ){redireccionar("../../index.php");} //VALIDO QUE ESTE LOGUEADO EL USUARIO
	
require_once("../../codebase/connector/grid_connector.php");

$conn = new GridConnector($conexion,"MySQL");
$conn->set_encoding("iso-8859-1");

$conn->dynamic_loading(100);
$conn->render_sql("SELECT actividades.*, u.usuario
                    FROM actividades 
                    INNER JOIN usuarios AS u ON u.id = actividades.idUsuario
                    ORDER BY actividades.fecha", "id","descripcion, usuario, fecha","id");
?>