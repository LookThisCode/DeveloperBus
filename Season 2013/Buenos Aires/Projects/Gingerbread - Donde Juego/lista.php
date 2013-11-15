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
    $( "#accordion" ).accordion();
  });
  
  function reservar(){
  $( "#dialog-confirm" ).dialog({
      resizable: false,
      width:400,
      height:200,
      closeOnEscape: true,
      modal: true,
      buttons: {
        "Aceptar": function() {
          window.location.href='reserva.html';
        },
        "Cancelar": function() {
          $( this ).dialog( "close" );
        }
      }
    });
    }
</script>
</head>

<body>
<div class="divfondo">
	<?php include("menu.php"); ?>
	<div id="accordion">
	  <h3>Cancha 1</h3>
	  <div>
	    <table class="lista" width="100%">
	    	<tr bgcolor="#3366FF" style="opacity: 0.5;">
	    		<td  class="titulolista">
	    			Serrano Corner - Serrano 250 - Tel. 4464-3948
	    		</td>
	    	</tr>
	    	<tr>
	    		<table class="lista">
	    			<tr>
	    				<td><img src="images/futbol1.jpg"/></td>
	    				<td rowspan="5"><iframe width="456" height="350" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://maps.google.com/maps?f=q&amp;source=s_q&amp;hl=es&amp;geocode=&amp;q=av+corrientes+5600&amp;aq=&amp;sll=-34.600256,-58.436944&amp;sspn=0.010615,0.021136&amp;ie=UTF8&amp;hq=&amp;hnear=Av+Corrientes+5600,+Villa+Crespo,+Buenos+Aires,+Argentina&amp;t=m&amp;ll=-34.594498,-58.441515&amp;spn=0.015685,0.039053&amp;z=14&amp;output=embed"></iframe><br/></td>
	    			</tr>
	    			<tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
	    			<tr colspan="2">
	    				<td>Canchas amplias, excelente iluminaci&oacute;n, lockers... <br/>Te esperamos!</td>
	    			</tr>
	    			<tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>

	    			<tr colspan="2">
	    				<td>
	    					<input type="checkbox" name="option1" value="0" checked/>C&eacute;sped<br>
							<input type="checkbox" name="option2" value="1"/>C&eacute;sped sint&eacute;tico<br>
							<input type="checkbox" name="option3" value="2"/>Cemento <br>	
	    				</td>
	    			</tr>
	    			<tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
					<tr colspan="2">
	    				<td>
	    					<ul>
	    					<li type="circle">Estacionamiento</li>
	    					<li type="circle">Duchas</li>
	    					</ul>	
	    				</td>
	    			</tr>
	    		</table>
	    	</tr>
	    </table>
	    <table align="right" class="lista">
	  	<tr align="right">
	  			<td>Precio de la Cancha: $520</td>						
	  			<td>
					<input type="button" class="botonreservar" onClick="reservar();"/>
				</td>
			</tr>	
	  	</table>
	  </div>
	  <h3>Cancha 2</h3>
	  <div>
	     <table class="lista" width="100%">
	    	<tr bgcolor="#3366FF" style="opacity: 0.5;">
	    		<td  class="titulolista">
	    			F&uacute;tbol Papi5 - Malabia 183 - Tel. 4398-5784
	    		</td>
	    	</tr>
	    	<tr>
	    		<table class="lista">
	    			<tr>
	    				<td><img src="images/futbol2.jpg"/></td>
	    				<td rowspan="5"><iframe width="456" height="350" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://maps.google.com/maps?f=q&amp;source=s_q&amp;hl=es&amp;geocode=&amp;q=av+corrientes+5600&amp;aq=&amp;sll=-34.600256,-58.436944&amp;sspn=0.010615,0.021136&amp;ie=UTF8&amp;hq=&amp;hnear=Av+Corrientes+5600,+Villa+Crespo,+Buenos+Aires,+Argentina&amp;t=m&amp;ll=-34.594498,-58.441515&amp;spn=0.015685,0.039053&amp;z=14&amp;output=embed"></iframe></td>
	    			</tr>
	    			<tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
	    			<tr colspan="2">
	    				<td>Te invitamos a una de las canchas m&aacute;s grandes<br/>de la Ciudad.</td>
	    			</tr>
	    			<tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>

	    			<tr colspan="2">
	    				<td>
	    					<input type="checkbox" name="option1" value="0" checked/>C&eacute;sped<br>
							<input type="checkbox" name="option2" value="1"/>C&eacute;sped sint&eacute;tico<br>
							<input type="checkbox" name="option3" value="2"/>Cemento <br>	
	    				</td>
	    			</tr>
	    			<tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
					<tr colspan="2">
	    				<td>
	    					<ul>
	    					<li type="circle">Estacionamiento</li>
	    					<li type="circle">Bar</li>
	    					</ul>	
	    				</td>
	    			</tr>

	    		</table>
	    	</tr>
	    </table>
	    <table align="right" class="lista">
	  		<tr align="right">
	  			<td>Precio de la Cancha: $480</td>						
	  			<td>
					<input type="button" class="botonreservar" onClick="reservar();"/>
				</td>
			</tr>		
	  	</table>
	  </div>
	  <h3>Cancha 3</h3>
	  <div>
	    <table class="lista" width="100%">
	    	<tr bgcolor="#3366FF" style="opacity: 0.5;">
	    		<td  class="titulolista">
	    			Canchitas "Nacho" - Ger&oacute;nimo Salguero 1441 - Tel. 4983-9383
	    		</td>
	    	</tr>
	    	<tr>
	    		<table class="lista">
	    			<tr>
	    				<td><img src="images/futbol3.jpg"/></td>
	    				<td rowspan="5"><iframe width="456" height="350" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://maps.google.com/maps?f=q&amp;source=s_q&amp;hl=es&amp;geocode=&amp;q=av+corrientes+5600&amp;aq=&amp;sll=-34.600256,-58.436944&amp;sspn=0.010615,0.021136&amp;ie=UTF8&amp;hq=&amp;hnear=Av+Corrientes+5600,+Villa+Crespo,+Buenos+Aires,+Argentina&amp;t=m&amp;ll=-34.594498,-58.441515&amp;spn=0.015685,0.039053&amp;z=14&amp;output=embed"></iframe></td>
	    			</tr>
	    			<tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
	    			<tr colspan="2">
	    				<td>Canchas amplias, excelente iluminaci&oacute;n, lockers... <br/>Te esperamos!</td>
	    			</tr>
	    			<tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>

	    			<tr colspan="2">
	    				<td>
	    					<input type="checkbox" name="option1" value="0"/>C&eacute;sped<br>
							<input type="checkbox" name="option2" value="1" checked/>C&eacute;sped sint&eacute;tico<br>
							<input type="checkbox" name="option3" value="2"/>Cemento <br>	
	    				</td>
	    			</tr>
	    			<tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
				    <tr  colspan="2">
				       <td></td>
				    </tr>
					<tr colspan="2">
	    				<td>
	    					<ul>
	    					<li type="circle">Duchas</li>
	    					<li type="circle">Techada</li>
	    					</ul>	
	    				</td>
	    			</tr>

	    		</table>
	    	</tr>
	    </table>
		<table align="right" class="lista">
	  		<tr align="right">
	  			<td>Precio de la Cancha: $500</td>						
	  			<td>
					<input type="button" class="botonreservar" onClick="reservar();"/>
				</td>
			</tr>	
	  	</table>
	  </div>
	  </div>
	   <table align="right">
	  		<tr align="right">						
	  			<td>
					<input type="button" class="botonotrabusq" onClick="window.location.href='index.php' "/>
				</td>
			</tr>	
	  	</table>
</div>
</div>
</body>
</html>