<br clear="left"/>
<div class="search-input" style="margin-top:5px">
	<form method="get" action="search">
	  <input type="text" name="query" value="<?php echo $query_terms ?>" placeholder="Busca musica, artistas, generos, zona" class="span7">
	</form>
</div>

<h2>Resultados relacionados con "<?php echo $query_terms; ?>"</h2>

<?php if(count($our_items) > 0) { ?>
	<h2>Nuestras bandas</h2>
	<?php foreach($our_items as $i=>$item) { ?>
		<div class="row-fluid band-search-item">
			<div class="span8 clearfix">
				<div class="pull-left">
					<img src="<?php echo base_url('assets/images/banda/banda'.($i%10+1).'.jpg') ?>" alt="">
				</div>
				<div>
					<a href="<?php echo site_url('band/'.$item->band_id) ?>"><?php echo $item->name; ?></a>
					<p><?php echo $item->description; ?></p>
				</div>
			</div>
			<?php if($i==1) {  ?>
			<div class="span4">
				<?php //VER PONERLO BIEN ?>
				<h3>Pr&oacute;ximos Shows</h3>
				<div class="event-display">
				  <div class="day pull-left">18</div>
				  <div class="event-description">
				    <p class="title">Evento interesante</p>
				    <p class="description">Alguna descripcion</p>
				  </div>
				</div>
			</div>
			<?php } ?>
		</div>
	<?php } ?>
<?php } ?>

<h2>Videos otras plataformas</h2>
<?php foreach($youtube_items as $item) { ?>
	<iframe width="350" height="270" src="//www.youtube.com/embed/<?php echo $item['id']['videoId'] ?>" frameborder="0" allowfullscreen></iframe> 
<?php } ?>


<?php foreach($tiny_items as $item) { ?>
	<object width="250" height="40" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"><param name="movie" value="http://grooveshark.com/songWidget.swf" /><param name="wmode" value="window" /><param name="allowScriptAccess" value="always" /><param name="flashvars" value="hostname=grooveshark.com&songID=<?php echo $item->SongID ?>&style=metal&p=0" /><object type="application/x-shockwave-flash" data="http://grooveshark.com/songWidget.swf" width="250" height="40"><param name="wmode" value="window" /><param name="allowScriptAccess" value="always" /><param name="flashvars" value="hostname=grooveshark.com&songID=<?php echo $item->SongID ?>&style=metal&p=0" /><span><a href="<?php echo $item->Url; ?>" ><?php echo $item->SongName ?></a></span></object></object>
<?php } ?>

