
function setup_gplus_button(){
	gapi.signin.render('customGPlusButton', {
		'callback': 'gplusCallback',
		'clientid': '522260385735-4rs1g082hunf0160hhcoc8qd6pefkbu5.apps.googleusercontent.com',
		'cookiepolicy': 'single_host_origin',
		'requestvisibleactions': 'http://schemas.google.com/AddActivity',
		'scope': 'https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/plus.login',
	});
}

function gplusCallback(authResult) {
	if (authResult['g-oauth-window']) {
		if(authResult['access_token']){
			user_object = {};

			//TODO Feio pra caraleo!!!!! Mas as duas chamadas sao assincronas
			gapi.client.load('plus','v1', function(){
				var request = gapi.client.plus.people.get({'userId': 'me'});
				request.execute(function(resp) {
					user_object.id = resp.id;
					user_object.first_name = resp.name.givenName;
					user_object.last_name = resp.name.familyName;
					user_object.url = resp.url;
					
					gapi.client.load('oauth2','v2',function(){
						gapi.client.oauth2.userinfo.get().execute(function(resp){
							user_object.email = resp.email;
							redirect_gplus_logged(user_object);
						});
					});
				});
			});



			

		}else if(authResult['error']){
			console.warn('Could not authenticate'); //TODO Show error notification
			console.warn(authResult['error']);
		}
			
	}
}

function redirect_gplus_logged(user_object){
	$.ajax({
		url: '/login_gplus',
		type: 'POST',
		dataType: 'json',
		data: {'gplus_object': JSON.stringify(user_object)},
		success : function(resp) {
			window.location.replace(resp.redirect_to);
		},
		error: function(err){
			console.warn(err); //TODO Show error notification
		}

	});
}