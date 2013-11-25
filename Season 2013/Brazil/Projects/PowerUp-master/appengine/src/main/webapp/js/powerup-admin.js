angular.module('powerup-admin', []).
    constant('googleConfig',{
        'apiRoot': document.location.origin + '/_ah/api',
        'client_id': '19047933307-qac31mnnbnf6mk8mtaqobs6stv42n9q3.apps.googleusercontent.com',
        'scope': ['https://www.googleapis.com/auth/plus.login',
        	'https://www.googleapis.com/auth/userinfo.email'],
        immediate: false
    }).
    run(function(googleConfig) { // instance-injector
        var apisToLoad;
        var callback = function() {
            if (--apisToLoad == 0) {
            }
        }
        apisToLoad = 3; // must match number of calls to gapi.client.load()
        gapi.client.load('powerup', 'v1', callback, googleConfig.apiRoot);
        gapi.client.load('oauth2', 'v2', callback);
        gapi.client.load('plus','v1', callback);
    }).
    config(function($routeProvider){
        $routeProvider.
            when('/dashboard', {templateUrl:'partials/alunos.html', controller: DashboardCtrl}).
            otherwise({redirectTo:'/home', templateUrl: 'partials/home.html' });
    }).
    filter('startFrom', function() {
        return function(input, start) {
            start = +start; //parse to int
            return input.slice(start);
        }
    }).
    factory('powerupServices', ['googleConfig', '$http', function(googleConfig, http) {

        var services = function() {
            return {

                signIn: function(mode, callback) {
                    var cfg = {
                        client_id : googleConfig.client_id,
                        mode: mode,
                        scope: googleConfig.scope
                        //, response_type: 'token id_token'
                    }

                    var mainCallback = function(authResult) {
//                        var token = gapi.auth.getToken();
//                        // Use id_token instead of bearer token
//                        token.access_token = token.id_token;
//                        gapi.auth.setToken(token);
                        callback.call(gapi.auth.authorize, authResult)
                    }

                    gapi.auth.authorize(cfg, mainCallback);
                },

                userInfo: function(callback){
                    var request = gapi.client.oauth2.userinfo.get().execute(callback);
                },

                profile: function(callback) {
                    var request = gapi.client.plus.people.get( {'userId' : 'me'} );
                    request.execute( callback );
                }
            }
        }

        return new services();
    }]).
    directive('json', function() {
        return {
            require: 'ngModel',
            link: function(scope, elm, attrs, ctrl) {
                ctrl.$parsers.unshift(function(viewValue) {
                    try {
                        var obj = angular.fromJson(viewValue)
                        // it is valid
                        ctrl.$setValidity('json', true);
                        return viewValue;
                    } catch(e) {
                        // it is invalid, return undefined (no model update)
                        ctrl.$setValidity('json', false);
                        return undefined;
                    }
                });
            }
        };
    });

function MainCtrl($scope, $location, $log, powerupServices) {

    var userAuthed = function(authResult) {
        $log.log(authResult)
        if (authResult && !authResult.error) {
            powerupServices.profile(function(profile) {
                $log.log(profile)
                if (!profile.error) {
                    $scope.signedIn = true;
                    $scope.user = profile;
                    $location.path("dashboard");
                    $scope.$apply();
                } else {
                    $log.log('Auth failed!!!')
                }
            });
        }
    };

    powerupServices.signIn(true, userAuthed)

    $scope.showSignInButton = function() {
        return !$scope.signedIn;
    }

    $scope.setRoute = function(route){
        $location.path(route);
    }

    $scope.signIn = function() {
        if (!$scope.signedIn) {
            powerupServices.signIn(false, userAuthed)
        } else {
            $scope.signedIn = false
        }
    }
    
    $scope.logout = function() {
       	$scope.signedIn = false
    }

}

function DashboardCtrl($scope, $http, $log, powerupServices){}

