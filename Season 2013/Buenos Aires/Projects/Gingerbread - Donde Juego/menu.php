	 <!-- Place this asynchronous JavaScript just before your </body> tag -->
    <script type="text/javascript">
      (function() {
       var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
       po.src = 'https://apis.google.com/js/client:plusone.js';
       var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
     })();
    </script>
		<span id="signinButton">
	  <span class="g-signin"
		data-callback="signinCallback"
		data-clientid="785007903160.apps.googleusercontent.com"
		data-cookiepolicy="single_host_origin"
		data-requestvisibleactions="http://schemas.google.com/AddActivity"
		data-scope="https://www.googleapis.com/auth/plus.login">
	  </span>
	</span>
	
	<!-- Place this asynchronous JavaScript just before your </body> tag -->
  <link href="http://fonts.googleapis.com/css?family=Roboto" rel="stylesheet" type="text/css">
  <script type="text/javascript">
  (function() {
    var po = document.createElement('script');
    po.type = 'text/javascript'; po.async = true;
    po.src = 'https://apis.google.com/js/client:plusone.js?onload=render';
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(po, s);
  })();

  function render() {
    gapi.signin.render('customBtn', {
      'callback': 'signinCallback',
      'clientid': '785007903160.apps.googleusercontent.com',
      'cookiepolicy': 'single_host_origin',
      'requestvisibleactions': 'http://schemas.google.com/AddActivity',
      'scope': 'https://www.googleapis.com/auth/plus.login'
    });
  }
  </script>
  <style type="text/css">
    #customBtn {
      display: inline-block;
      background: #dd4b39;
      color: white;
      width: 165px;
	  height:20px;
      border-radius: 5px;
      white-space: nowrap;
    }
    #customBtn:hover {
      background: #e74b37;
      cursor: hand;
    }
    span.label {
      font-weight: bold;
    }
    span.icon {
      background: transparent 5px 50% no-repeat;
      display: inline-block;
      vertical-align: middle;

      border-right: #bb3f30 1px solid;
    }
    span.buttonText {
      display: inline-block;
      vertical-align: middle;
      padding-left: 35px;
      padding-right: 35px;
      font-size: 14px;
      font-weight: bold;
      /* Use the Roboto font that is loaded in the <head> */
      font-family: 'Roboto',arial,sans-serif;
    }
  </style>
  <!-- In the callback, you would hide the gSignInWrapper element on a
  successful sign in -->

	<div style="height:100px" border=1>
				<table align="left">
			    	<tr>
				         <td>
				             <a href="contacto.php?r=1"><input type="button" class="botonimagen" value=""/></a>
				         </td>
			         </tr>
			         <tr>
				         <td style="text-align:center;">
				             <a href="admin">Ya estoy registrado</a>
				         </td>
			         </tr>
			    </table>
			    <table align="right">
			   <!-- 	<tr>
				         <td>
				             <a href="">Log-in </a> | <a href="">Registrarse </a>
				         </td>
			         </tr> -->
					 <tr>
					 <td>
						  <div id="gSignInWrapper">
						<div id="customBtn" class="customGPlusSignIn">
						  <span class="buttonText">Log In +Google</span>
						</div>
					  </div>
					 </td>
					 </tr>
			    </table>
	</div>
	<div style="height:300px;">

			    <table align="right">
			    	<tr>
					<td>
				             <a href="index.php"><input type="button" class="botonhome" value=""/></a>
				         </td>
				         <td>
				             <a href="propuesta.php"><input type="button" class="botonprop" value=""/></a>
				         </td>
				         <td>
				             <a href="lista2.php"><input type="button" class="botondesc" value=""/></a>
				         </td>
				         <td>
				             <a href="contacto.php"><input type="button" class="botoncontacto" value=""/></a>
				         </td>
			         </tr>
			    </table>
		<div class="googleplusarriba">
					<!-- Inserta esta etiqueta en la sección "head" o justo antes de la etiqueta "body" de cierre. -->
			<script type="text/javascript" src="https://apis.google.com/js/plusone.js">
			  {lang: 'es', parsetags: 'explicit'}
			</script>

			<!-- Inserta esta etiqueta donde quieras que aparezca Botón +1. -->
			<div class="g-plusone" data-size="tall"></div>

			<!-- Inserta esta invocación donde quieras. -->
			<script type="text/javascript">gapi.plusone.go();</script>
		</div>
	</div>