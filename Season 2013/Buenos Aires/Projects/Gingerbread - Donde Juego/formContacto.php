<?php
$enviadoCorrecto = $presioneSubmit = $emailOK = 0;
$nombre = $email = $telefono = $asunto = $mensaje = "";
if ( (isset($_GET['cod'])) and ($_GET['cod']!='')){
$codigoProd = $_GET['cod'];
$asunto= "Interes en compra del articulo nº $codigoProd";
}
if ( (isset($_GET['asunto'])) and ($_GET['asunto']!='')){
$asunto = $_GET['asunto'];
}

	if(isset($_POST['submit'])){
		$presioneSubmit=1;
		//si fue apretado SUBMIT, entra
		$nombre = $_POST['nombre'];
		$email = $_POST['email'];
		$telefono = $_POST['telefono'];
		$asunto = $_POST['asunto'];
		$mensaje = $_POST['mensaje'];
		
		date_default_timezone_set('America/Argentina/Buenos_Aires');
		setlocale(LC_TIME, 'spanish');

		$dia = strftime("%d/%m/%Y");
		$hora = strftime("%H:%M:%S");

		$email=filter_var($email, FILTER_SANITIZE_EMAIL);
		$emailOK = filter_var($email, FILTER_VALIDATE_EMAIL);
		$telefono= filter_var($telefono, FILTER_SANITIZE_NUMBER_INT);
		$nombre = preg_replace("/[^a-zA-Z\sáéíóúñÑ]/", "", (filter_var($nombre, FILTER_SANITIZE_STRING)));				
		$mensaje = filter_var ($mensaje, FILTER_SANITIZE_STRING);
					
		if ( ($nombre!='') and ($emailOK) and ($mensaje!='')) {			
			$header = 'From: ' . $email . " \r\n";
			$header .= "X-Mailer: PHP/" . phpversion() . " \r\n";
			$header .= "Mime-Version: 1.0 \r\n";
			$header .= "Content-Type: text/plain";

			$texto = "Nombre: " . $nombre . " \r\n";
			if (!($telefono=="")){
				$texto .= "Telefono: " . $telefono . " \r\n\n";
			}
			$texto .= $mensaje . ".\r\n\n";
			$texto .= "Enviado el " . $dia . " a las " . $hora ;

			$para = 'contacto@dondejuego.com.ar'; //CAMBIAR PARA

			if ($asunto!=''){ $asunto= '(sin asunto)'; }				
			
			mail($para, $asunto, utf8_decode($texto), $header);
			$enviadoCorrecto=1;
			echo '<div class="form">Se ha enviado correctamente el E-Mail. En breve nos contactaremos con usted.</div>';		
			//para que en el form no aparezcan los valores recien enviados
			$nombre = $email= $telefono = $asunto = $mensaje = "";
		}
	}?>
		<div class="formContacto">
			<form action="contacto.php" method="post">
				Nombre * <br><div><input class="<?php if (($nombre=='') and ($presioneSubmit==1)) echo 'error';?>" type="text" name="nombre" maxlength="60" value="<?php echo $nombre; ?>"/></div>
				Email * <br><div><input class="<?php if ((!$emailOK) and ($presioneSubmit==1)) echo 'error'; ?>" type="text" name="email" maxlength="60" value="<?php echo $email; ?>"/></div>
				Telefono <br><div><input type="text" name="telefono" maxlength="30" value="<?php echo $telefono; ?>"/></div>
				Asunto  <br><div><input type="text" name="asunto" maxlength="100" value="<?php echo $asunto.$r; ?>"/></div>
				Mensaje * <br><div><textarea class="textarea <?php if (($mensaje=='') and ($presioneSubmit==1)) echo 'error';?>" name="mensaje" rows="11" ><?php echo $mensaje; ?></textarea></div>
						<div><input type="submit" name="submit" value="ENVIAR"></div>
			</form>	
		</div>		