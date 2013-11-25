var req = createXMLHttpRequestObject();
var geocoder;
var bounds = new google.maps.LatLngBounds();
var markersArray = [];
var map;
var originIcon = 'https://chart.googleapis.com/chart?chst=d_map_pin_letter&chld=O|FFFF00|000000';
var destinationIcon = 'https://chart.googleapis.com/chart?chst=d_map_pin_letter&chld=D|FF0000|000000';
var servicos;
var distMax;

function initialize(idDiv) {
	var opts = {
		center : new google.maps.LatLng(-7.24, -39.41),
		zoom : 16
	};
	map = new google.maps.Map(document.getElementById(idDiv), opts);
	geocoder = new google.maps.Geocoder();
}

function createXMLHttpRequestObject() {
	var req = null;
	if (window.XMLHttpRequest) {
		req = new XMLHttpRequest();
	} else if (window.ActiveXObject) { // IE
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}
	return req;
}

function getEndereco(cep, callback){
	geocoder = new google.maps.Geocoder();
	geocoder.geocode({
		'address' : cep
	}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			var geocoder2 = new google.maps.Geocoder();
			geocoder2.geocode({
				'location' : results[0].geometry.location
			}, function(results2, status) {
				if (status == google.maps.GeocoderStatus.OK) {
					callback(results2[0].formatted_address);
				} else {
					alert('Geocode 2 was not successful for the following reason: '
							+ status);
				}
			});
		} else {
			alert('Geocode was not successful for the following reason: '
					+ status);
		}
	});
}



function getServicos(idEnd, idDist) {
	var endereco = document.getElementById(idEnd).value;
	var distancia = document.getElementById(idDist).value;
	geocoder.geocode({
		'address' : endereco
	}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			var param = "distanciaMax=" + distancia;
			param += "&latitude=" + results[0].geometry.location.lat();
			param += "&longitude=" + results[0].geometry.location.lng();
			param += "&origem=" + endereco;
			distMax = distancia;
			req.open("POST", "getServicos.jsp", true);
			req.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			req.send(param);
			req.onreadystatechange = resultadoServicos;
		} else {
			alert('Geocode was not successful for the following reason: '
					+ status);
		}
	});
}

function getServicos2() {
	return servicos;
}
function resultadoServicos() {
	if (req.readyState == 4) {
		var end = req.responseText;
		var servicos = eval("(" + end + ")");
		var service = new google.maps.DistanceMatrixService();
		var dest = new Array();
		for (var i = 0; i < servicos.servico.length; i++) {
			dest[i] = new google.maps.LatLng(servicos.servico[i].latitude,
					servicos.servico[i].longitude);
		}
		service.getDistanceMatrix({
			origins : [ servicos.origem ],
			destinations : dest,
			travelMode : google.maps.TravelMode.DRIVING,
			avoidHighways : false,
			avoidTolls : false
		}, callback);

	}
}

function getCoordenada(login) {
	var param = "login=" + login;
	req.open("POST", "getEndereco.jsp", true);
	req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	req.send(param);
	req.onreadystatechange = resultadoEndereco;
}

function resultadoEndereco() {
	if (req.readyState == 4) {
		var end = req.responseText;
		var endereco = eval("(" + end + ")");
		var service = new google.maps.DistanceMatrixService();
		service.getDistanceMatrix({
			origins : [ "Rua Carolino Sucupira, 60, Crato-CE" ],
			destinations : endereco.enderecos,
			travelMode : google.maps.TravelMode.DRIVING,
			avoidHighways : false,
			avoidTolls : false
		}, callback);

	}
}

function callback(response, status) {
	if (status != google.maps.DistanceMatrixStatus.OK) {
		alert('Error was: ' + status);
	} else {
		var origins = response.originAddresses;
		var destinations = response.destinationAddresses;
		var outputDiv = document.getElementById('outputDiv');
		outputDiv.innerHTML = '';
		deleteOverlays();
		servicos = "";
		for (var i = 0; i < origins.length; i++) {
			var results = response.rows[i].elements;
			addMarker(origins[i], false);
			for (var j = 0; j < results.length; j++) {
				//if (results[j].distance <= distMax) {
					addMarker(destinations[j], true);
					servicos += '{"servico":"'+destinations[j]+'"}';
					outputDiv.innerHTML += origins[i] + ' to '
							+ destinations[j] + ': ' + results[j].distance.text
							+ ' in ' + results[j].duration.text + '<br>';
				//}
			}
		}
	}
}

function addMarker(location, isDestination) {
	var icon;
	if (isDestination) {
		icon = destinationIcon;
	} else {
		icon = originIcon;
	}
	geocoder.geocode({
		'address' : location
	}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			bounds.extend(results[0].geometry.location);
			map.fitBounds(bounds);
			var marker = new google.maps.Marker({
				map : map,
				position : results[0].geometry.location,
				icon : icon
			});
			markersArray.push(marker);
		} else {
			alert('Geocode was not successful for the following reason: '
					+ status);
		}
	});
}

function deleteOverlays() {
	for (var i = 0; i < markersArray.length; i++) {
		markersArray[i].setMap(null);
	}
	markersArray = [];
}