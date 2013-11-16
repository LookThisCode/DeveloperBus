<?php
		if( isset($_GET['r']) && $_GET['r'] != "")
			$r = "Quiero promocionar mi cancha";
		else			
			$r="";
?>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>&iquest;D&oacute;nde Juego?
</title>
<link rel="stylesheet" type="text/css" href="style.css" media="screen" />
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script>
  $(function() {
  	$.datepicker.regional['es'] = {
        closeText: 'Cerrar',
        prevText: '<Ant',
        nextText: 'Sig>',
        currentText: 'Hoy',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene','Feb','Mar','Abr', 'May','Jun','Jul','Ago','Sep', 'Oct','Nov','Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
        dayNamesShort: ['Dom','Lun','Mar','Mié','Juv','Vie','Sáb'],
        dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','Sá'],
        weekHeader: 'Sm',
        dateFormat: 'dd/mm/yy',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: ''
    };
    $.datepicker.setDefaults($.datepicker.regional['es']);
    $( "#cuando" ).datepicker({ minDate: 0, maxDate: "+1M" });
    
   	$("#juego").change(function () {

    });
  });
</script>
</head>

<body>
<div class="divfondo">
	<?php include("menu.php"); ?>
	<!-- HASTA ACA HEAD-->
	<div style="text-align:center;">
		<iframe width="640" height="480" src="//www.youtube.com/embed/A88ihEinUc8" frameborder="0" allowfullscreen></iframe>
	</div>		
	<div style="text-align:center;"><img src="images/texto-propuesta.png"></div>
		<!-- FIN BODY-->
	<div class="espacio"></div>
	</div>
	</div>
</body>
</html>