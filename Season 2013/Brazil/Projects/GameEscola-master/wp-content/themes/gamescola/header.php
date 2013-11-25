<?php
/*
Theme Name: GamEscola
Theme URI: 
Author: GamEscola - Hussani - Michelli - Tiago - Silvia
Author URI: https://plus.google.com/u/0/b/112977374414232772041/
Description: Aplicação online que visa promover a melhoria no ensino básico
Version: 0.1

*/

if ( is_user_logged_in() && is_home() ) { 
	wp_redirect( get_bloginfo('url').'?perguntas_quiz=quiz-para-1o-ano' );
	exit;
}
?>
<!DOCTYPE html>
<!--[if IE 7]>
<html class="ie ie7" <?php language_attributes(); ?>>
<![endif]-->
<!--[if IE 8]>
<html class="ie ie8" <?php language_attributes(); ?>>
<![endif]-->
<!--[if !(IE 7) | !(IE 8)  ]><!-->
<html <?php language_attributes(); ?>>
<!--<![endif]-->
<head>
	<meta charset="<?php bloginfo( 'charset' ); ?>">
	<meta name="viewport" content="width=device-width">
	<title><?php bloginfo('name'); wp_title(); ?></title>
	<link rel="shortcut icon" href="<?php bloginfo('template_directory'); ?>/favicon.ico" />
	<link rel="profile" href="http://gmpg.org/xfn/11">
	<link rel="pingback" href="<?php bloginfo( 'pingback_url' ); ?>">
	<link rel="stylesheet" href="<?php bloginfo( 'template_url' ); ?>/css/normalize.css" />
	<link rel="stylesheet" href="<?php bloginfo( 'template_url' ); ?>/css/foundation.css" />	
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,700,700italic' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" type="text/css" media="screen" href="<?php bloginfo( 'stylesheet_url' ); ?>" />
	<link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
	<link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/jquery.bxslider.css" rel="stylesheet">
		
	<script type="text/javascript" src="<?php bloginfo('template_url'); ?>/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<?php bloginfo('template_url'); ?>/js/jquery-ui-1.9.2.custom.min.js"></script>
	<script type="text/javascript" src="<?php bloginfo('template_url'); ?>/js/foundation.min.js"></script>
	<script type="text/javascript" src="<?php bloginfo('template_url'); ?>/js/jquery.placeholder.min.js"></script>
	<?php if(is_tax()){ ?><script type="text/javascript" src="<?php bloginfo('template_url'); ?>/js/jquery.bxslider.min.js"></script><?php } ?>
	<script type="text/javascript" src="<?php bloginfo('template_url'); ?>/js/index.js"></script>


	<!--[if lt IE 9]>
	<script src="<?php echo get_template_directory_uri(); ?>/js/html5.js"></script>
	<![endif]-->	
</head>

<body>
	
		<header id="masthead" class="site-header fixed" role="banner">
			<?php if ( is_user_logged_in() ) { ?>
			<?php
				$current_user = wp_get_current_user();
				$all_meta_for_user = get_user_meta( $current_user->ID );															
			?>
				<section class="row log-dados">
					<div class="large-4 columns logo"><a href="<?php bloginfo( 'url' ); ?>"><img src="<?php bloginfo('template_url'); ?>/images/logo.png" alt="Logo Gamescola" /></a></div>
					<div class="offset-large-2 large-6 columns user-data right">
						<span><h2><?php echo $current_user->user_firstname .' '.$current_user->user_lastname; ?> </h2>
						<p>1º ano</p>
						<a class="log-out" href="<?php echo wp_logout_url( home_url() ); ?>">Sair</a>
						</span>						
						<?php echo get_avatar( $current_user->ID, 65); ?>
						<div class="award">
							<img src="<?php bloginfo('template_url'); ?>/images/bee.png" alt="award" />
							<p class="prem">Meus Pontos</p>
							<p class="prem-num">Total: <?php echo $all_meta_for_user['pontos'][0]; ?></p>
						</div>
					</div>
					
				</section>
			<?php }else{ ?>
				<section class="row">
					<div class="large-4 columns logo"><img src="<?php bloginfo('template_url'); ?>/images/logo.png" alt="Logo Gamescola" /></div>
					<div class="offset-large-4 large-4 columns log-g">
                        <span id="signinButton">
                            <span
                            class="g-signin"
                            data-callback="signinCallback"
                            data-clientid="310116777262-i5sbrk09gusp949ecfiuimgehenp4vi3.apps.googleusercontent.com"
                            data-cookiepolicy="single_host_origin"
                            data-requestvisibleactions="http://schemas.google.com/AddActivity"
                            data-scope="https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.email">
                            </span>
                        </span>
					</div>
					
				</section>
			<?php } ?>
		</header><!-- #masthead -->