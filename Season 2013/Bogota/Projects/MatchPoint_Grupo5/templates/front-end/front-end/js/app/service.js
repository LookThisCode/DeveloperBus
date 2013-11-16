angular.module('myApp.services', ['ngResource'])
 	.factory('AngularIssues', function($resource){
    	return $resource('http://172.20.15.70:8080/api/service', {})
  	})
  	.value('version', '0.1');
