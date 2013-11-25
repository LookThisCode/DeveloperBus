$(function() {
	var servidor = "";
	
	$.getJSON( servidor+'/rest/v1/alows', function( data ) {
	   var items = [];
	   $.each( data, function( key, val ) {
	    items.push( '<div class="list notify" data-id="'+ val.alow.id +'"><div class="info"><span class="group">Vendas</span> <h3>'+ val.alow.texto +'</h3></div><ul class="options"><li class="report">Relatorio</li><li class="exlude icon-trash"></li></ul></div>' );
	  });

	  $( "<div/>", {
    		"class": "my-new-list",
    		html: items.join( "" )
  		}).appendTo( ".main" );

	  	//Excluir
	  	$('.options li.exlude').click( function(e) {
	  		console.log( $(this).parent().parent().data('id') );
	  		$.ajax({
	  			
		        url: servidor+'/rest/v1/alows/'+$(this).parent().parent().data('id'),
		        type: 'delete',
		        dataType: 'json',
		        //success: function(result) {
		        	//$(this).parent().parent().remove();
	        	//}
	   	 	});
	  		
	  		$(this).parent().parent().remove();
	  		
	  		e.preventDefault();
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

	function ConvertFormToJSON(form){
	    var array = jQuery(form).serializeArray();
	    var json = {};

	    jQuery.each(array, function() {
	        json[this.name] = this.value || '';
	    });

	    return json;
	}
	
	//Post Alow
	$('#sendalow').click( function(e) {
	    $.ajax({
	        url: servidor+'/rest/v1/alows',
	        type: 'post',
	        dataType: 'json',
	        contentType: "application/json;charset=UTF-8",
	        data: JSON.stringify(ConvertFormToJSON( $('form.notificacao') ))
   	 	});
	    
	    e.preventDefault();
	});

});