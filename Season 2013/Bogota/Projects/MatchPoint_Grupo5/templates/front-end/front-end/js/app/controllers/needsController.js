function needsController( $scope, $http ){

	$scope.url = 'http://172.20.15.40:8080/api/user'

	$scope.getAllByUser = function( id_user ){

		var xhr = $http.get( $scope.url + '/' + id_user + '/needs' );

		xhr.success( function( data ){
			
			console.info( data );

			$scope.needs_user = data.needs;
		} );
	}	
}