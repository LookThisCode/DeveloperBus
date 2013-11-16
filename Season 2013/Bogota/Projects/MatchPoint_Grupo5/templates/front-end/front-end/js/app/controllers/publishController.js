function publishController( $scope, $http ){

	$scope.life = 5;

	$scope.url_publish = 'http://172.20.15.40:8080/api/need';

	$scope.url_services = 'http://172.20.15.40:8080/api/service';
	
	$scope.getAllServices = function(){

		var xhr = $http.get( $scope.url_services );

		xhr.success( function( data ){
			$scope.services = data.items;
			console.log( data.items );
		} );

		xhr.error( function( data ){
			console.log( 'Error!. Server say: ' + data );
		} );
	}

	$scope.publish = function(){

		if( $scope.city == 'any' ){
			$scope.local_ubication = 'false'
			$scope.city = '';
		}
		else{
			$scope.local_ubication = 'true'
		}

		$scope.json_data = {
			description: $scope.description,
			service: $scope.category,
			local_ubication: $scope.local_ubication,
			delivery_time: $scope.deadline,
			budget: $scope.budget,
			life: $scope.life,
			ubication: $scope.city
		};

		var xhr = $.ajax({
			type: 'POST',
			url: $scope.url_publish,
			data: $scope.json_data,
			dataType: 'json'
		});

		/*var xhr = $http.post( $scope.url_publish,
					$scope.json_data );*/

		xhr.success( function( data ){
			console.info( data );
		} );

		console.info( $scope.json_data );
	}
}