$(document).ready(function(){
	$(document).foundation();
	$('input, textarea').placeholder();
	
	// GOOGLE TEXT TO SPEECH API
	$( ".icon-som" ).click(function() {
		var text = $(this).attr('alt').split(' ').join('%20');
		playSound(text);
	});
    
	function playSound(text) {
		var text = 'Teste';
		var url = 'http://translate.google.com/translate_tts?tl=pt_BR&q='+text;
		console.log(url);
		$('body').append('<audio id="my-audio" autoplay="autoplay"/>').find('audio').attr('src',url);
		//var a = new Audio('http://translate.google.com/translate_tts?tl=pt_BR&q=Como%20voc%C3%AA%20esta%20se%20sentindo%20hoje%3F');
		//a.play();
	}
	
	// SLIDER INTERNO
	slider = $('.bxslider').bxSlider({
  		minSlides: 2,
  		maxSlides: 3,
  		slideWidth: 960,
  		infiniteLoop: false,
  		hideControlOnEnd: true
	});
     
    // ANIMAÇÕES ABELHA
	$( ".iniciar_jogo" ).click(function() {
		$( ".iniciar_jogo img" ).animate({
			opacity: 0,
		    marginLeft: "296px",
		}, 800, function() {
			slider.goToNextSlide();
			var title = $('#item_10').attr('data-title');
			$("#item_post").hide().html(title).fadeIn(250);
		});
	});
	
	
	
	/*
	$( ".icon-som" ).click(function() {
		var text = encodeURIComponent($(this).attr('alt'));
		var a = new Audio('http://translate.google.com/translate_tts?tl=pt_BR&q='+text+'&#8221');
		a.play();
	});
	*/
        
      $(".small_bee").click(function(){
            $.get( "wp-content/themes/gamescola/test.php", function( data ) {
                  var resp = eval('('+data+')');
                  for (i=0;i<resp.length;i++) {
                             
                              alert( resp[i]);
                        }
                  
            });      
      });
      
    
      
});