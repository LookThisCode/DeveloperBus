function servicesController( $scope, $http ){

	$scope.url = 'http://localhost:8080/api/service';
	
	/*$scope.services = [{id: 1, name: 'Consultoria en general'},
			{id: 2, name: 'Generacion informe DIAN'},
			{id: 3, name: 'Reportes a transito'}];*/

	$scope.getAll = function(){

		var xhr = $http.get( $scope.url );

		xhr.success( function( data ){
			$scope.services = data.items;
			console.log( data );
		} );

		xhr.error( function( data ){
			console.log( 'Error!. Server say: ' + data );
		} );
	}

	$scope.search = function( word ){
		console.info( word );
	}
}