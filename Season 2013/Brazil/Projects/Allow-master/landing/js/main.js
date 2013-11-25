$(function() {

	$.getJSON( "alows.json", function( data ) {
	   var items = [];
	   $.each( data, function( key, val ) {
	    items.push( '<div class="list notify"><div class="info"><span class="group">'+ val.grupoId +'</span> <h3>'+ val.texto +'</h3></div><ul class="options"><li class="report">Relatorio</li><li class="exlude icon-trash"></li></ul></div>' );
	  });

	  $( "<div/>", {
    		"class": "my-new-list",
    		html: items.join( "" )
  		}).appendTo( ".main" );

	  	$('.options li.exlude').click( function() {

              $(this).parent().parent().remove();

		});

	});

	$('.next-step').click( function(e) {
		$_next = $(this).attr('href');
		$('.etapa1').fadeOut( "fast", function() {
			$($_next).fadeIn( "fast");
 		 });

		e.preventDefault();
	});

	$('.modal-close').click( function() {
		$('.etapa1').css('display', 'block');
		$('.etapa2').css('display', 'none');
	});


	$('.menu').click( function(e) {

		if ( $( window ).width() < 840)
			$('.sidebar nav').toggleClass('open');

		e.preventDefault();
	});

	//Colocar url aqui
	$('input.submit').click( function() {
	    alert($('form.notificacao').serialize());
	});

});