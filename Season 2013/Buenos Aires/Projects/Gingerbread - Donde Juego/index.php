<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>&iquest;D&oacute;nde Juego?
</title>
<link rel="stylesheet" type="text/css" href="style.css" media="screen" />
<link rel="stylesheet" type="text/css" href="slider.css" media="screen" />
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="jquery-slider.js"></script>
<script src="jquery-1.7.1.min.js"></script>
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
	<div class="listas">
		<form>
				<table widht=100% border="0" id="tablebody" align="center">
				    <tr>
				       <td></td>
				    </tr>   
					<tr>
						<td align=center style="text-align:center;">
							<input type="text" class="searchfield" value="&iquest;Ya sabes d&oacute;nde?..." onfocus="if (this.value == '&iquest;Ya sabes d&oacute;nde?...') {this.value = '';}" onblur="if (this.value == '') {this.value = '&iquest;Ya sabes d&oacute;nde?...';}" size="20"/>
			
			
						</td>
						<td><input type="button" class="searchbutton"/></td>
						<td colspan="3">
					</tr>
					<tr>
						<td colspan="5"/>
					</tr>
					<tr>
						<td colspan="5"/>
					</tr>
					<tr>
						<td colspan="5"/>
					</tr>
					<tr>
						<td colspan="5"/>
					</tr>
					<tr>
						<td><div class="styled-select">
							<select id="juego" class="rounded">
							  <option value="0">&iquest;Qu&eacute; juegas?</option>
							  <option value="1">F&uacute;tbol</option>
							  <option value="2">Tennis</option>
							</select></div>
						</td>
						<td width="200px"><div class="styled-select">
							<select id="cuantos" class="rounded">
							   <option value="0">&iquest;Cu&aacute;ntos?</option>
							   <option value="1">5</option>
							   <option value="2">10</option>
							</select></div>
						</td>
						<td width="200px"><div class="styled-select">
							<select id="donde" class="rounded">
							  <option value="0">&iquest;D&oacute;nde?</option>
							  <option value="1">Almagro</option>
							  <option value="2">Belgrano</option>
							  <option value="3">Caballito</option>
							  <option value="4">Colegiales</option>
							  <option value="5">Flores</option>
							  <option value="6">Monserrat</option>
							  <option value="7">Retiro</option>
							  <option value="8">San Nicol&aacute;s</option>
							  <option value="10">Villa Crespo</option>
							</select></div>
						</td>
						<td width="200px">
							<input type="text" id="cuando" class="rounded" size="12" value="&iquest;Cu&aacute;ndo?"/>
						</td>
						<td width="200px" align="right"><div class="styled-select">
							<select id="hora" class="rounded">
							  <option value="0">&iquest;A qu&eacute; hora?</option>
							  <option value="1">18:00 - 19:00</option>
							  <option value="2">19:00 - 20:00</option>
							  <option value="3">20:00 - 21:00</option>
							</select></div>			
						</td>
					</tr>
					<tr  colspan="5">
				       <td></td>
				    </tr>
				    <tr  colspan="5">
				       <td></td>
				    </tr>
				   	<tr  colspan="5">
				       <td></td>
				    </tr>
				    <tr  colspan="5">
				       <td></td>
				    </tr>
				    <tr  colspan="5">
				       <td></td>
				    </tr>
				    <tr  colspan="5">
				       <td></td>
				    </tr>
					<tr align="center">
						<td colspan="4"/>
						<td>
							<input type="button" class="botonbuscar" onClick="window.location.href='lista.php' "/>
						</td>
					</tr>	
				</table>
				</form>
		</div>
		<div>
		</div>
		<div>
			<table>
				<tr>
				       <td><br/></td>
				 </tr>
				    <tr>
				       <td><br/></td>
				    </tr>
				   	<tr>
				       <td><br/></td>
				    </tr>
				    <tr>
				       <td><br/></td>
				    </tr>
				    <tr>
				       <td><br/></td>
				    </tr>
				    <tr>
				       <td><br/></td>
				</tr>
				<tr>
					<td>
						<a href="lista2.php">
							<img src="images/CANCHA-DCTO.jpg"></img>
						</a>
					</td>
				</tr>
			</table>
  		</div>
		<div>
			<table>
				<tr>
				       <td><br/></td>
				 </tr>
				    <tr>
				       <td><br/></td>
				    </tr>
				   	<tr>
				       <td><br/></td>
				    </tr>
				    <tr>
				       <td><br/></td>
				    </tr>
				<tr>
					<td>
						<img src="images/destacados.jpg"></img>
					</td>
				</tr>
			</table>
  		</div>
		<div class="div2">
        <div id="mcts1">
            <a href="canchasdestacadas.php"><img src="images/destacado1.png" /></a>
            <img src="images/destacado2.png" />
            <img src="images/destacado3.png" />
            <img src="images/destacado4.png" />
            <img src="images/destacado5.png" />
        </div>
    </div>
	</div>
</body>
</html>