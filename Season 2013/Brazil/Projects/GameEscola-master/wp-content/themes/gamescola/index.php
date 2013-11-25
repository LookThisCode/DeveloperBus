<?php
/*
Theme Name: GamEscola
Theme URI: 
Author: GamEscola - Hussani - Michelli - Tiago - Silvia
Author URI: https://plus.google.com/u/0/b/112977374414232772041/
Description: Aplicação online que visa promover a melhoria no ensino básico
Version: 0.1

*/

get_header();
?>
<section class="cont-home">
	
	<ul class="cont-slider hide-for-small" data-orbit >
		<li>
			<img src="<?php bloginfo('template_url'); ?>/images/slider.jpg" alt="Slider 1" />
			<div class="row text-slider">
				<p>Educar é impregnar de sentido</p>
				<p>o que fazemos</p>
				<p>a cada instante!</p>
			</div>
			<div class="row button-slider">
				<a href="#" >Faça parte agora!</a>
			</div>
		</li>
	</ul>	
	<section class="row dados-land">
		<section class="large-4 columns">
			<h2>O que é o Gamescola?</h2>
			<section class="txt-dados">
				<p>Gamescola é uma aplicação online que visa promover a melhoria no ensino básico através do feedback dos alunos,
				utlizando técnicas e serviços de gamefication.	</p>			
				<p>Risus sagittis parturient ac tempor, parturient odio pid!
				Elementum vut cras natoque ultricies urna mauris nec! Rhoncus. Sed, augue porta! Porta nec enim ac! Lacus augue! Velit a, augue, a et, augue odio velit montes enim, tempor penatibus cursus? Egestas, proin cum? Adipiscing elementum, magna, sed aliquet nunc, turpis adipiscing scelerisque ac habitasse mid? Placerat ultricies odio hac nascetur placerat.	</p>
			</section>
			<div class="button-slider show-for-small">
		<a href="#" >Faça parte agora!</a>
	</div>
		</section>
		<section class="large-4 columns">
			<h2>Fique por dentro</h2>
			<section class="txt-dados">
				<div class="top-feed">
					<img src="<?php bloginfo('template_url'); ?>/images/logo_bus.jpg" alt="Logo Google Bus" />
				</div>
				<div class="body-feed">
					<p>Risus sagittis parturient ac tempor, parturient odio pid! Elementum vut cras natoque ultricies urna mauris nec! Rhoncus.</p>
					<p>Risus sagittis parturient ac tempor, parturient odio pid! Elementum vut cras natoque ultricies urna mauris nec! Rhoncus.</p>
					<p>Risus sagittis parturient ac tempor, parturient odio pid! Elementum vut cras natoque ultricies urna mauris nec! Rhoncus.</p>					
				</div>
				<div class="foot_feed">
					#devbusBrasil
				</div>
			</section>
		</section>
		<section class="large-4 columns">
			<h2>Fale Conosco</h2>
			<form class="row form-contato" method="post" action="#">
				<label for="nome">Nome</label>
				<input type="text" required placeholder="Nome*" name="nome" id="nome" />
				<label for="email">Telefone</label>
				<input type="email" placeholder="Telefone" name="telefone" id="telefone"  />
				<label for="email">E-mail</label>
				<input type="email" required placeholder="E-mail*" name="email" id="email" aria-required="true" />
				<label for="mensagem">Mensagem</label>
				<textarea required placeholder="Mensagem*" name="mensagem" id="mensagem" aria-required="true"></textarea>
				<input type="submit" value="Enviar" id="bt-contato"  />			
			</form>
		</section>
	</section>
</section>

<?php get_footer(); ?>