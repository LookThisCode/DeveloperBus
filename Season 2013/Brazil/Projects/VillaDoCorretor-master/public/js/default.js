$(function(){
	$('.flexslider').flexslider({
	  animation: "slide",
	  animationLoop: false,
	  itemWidth: 200,
	  itemMargin: 5,
	  minItems: 2,
	  maxItems: 4,
	  start: function(slider){
	    $('body').removeClass('loading');
	  }
	});

	$('#map-canvas').fitVids();

});