function persistenceController( $scope, $http ){

	$scope.url = 'http://172.20.15.70:8080/api/service';
	//$scope.url = '/api';

	$scope.fetch = function( method ){

		var xhr = $http({
			method: method,
			dataType: 'json',
			url: $scope.url
		});
			
	 	xhr.success( function( data, status, headers, config ) {

	    	console.info( 'Server response: ' + data );
	  	});
		  
	  	xhr.error(function(data, status, headers, config) {
	    	console.info( 'Server error: ' + data );
		});

	}	
}
