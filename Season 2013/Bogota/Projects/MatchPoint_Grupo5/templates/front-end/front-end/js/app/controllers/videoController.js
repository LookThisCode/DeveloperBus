function videoController( $scope ){

	$scope.show = function( url ){
		$('.video-reel').magnificPopup({ 
		  	items: {
      			src: url
    		},
    		type: 'iframe' 
		});
	}
}