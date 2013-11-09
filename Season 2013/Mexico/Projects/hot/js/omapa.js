/*! js Hot it | Objeto Mapa | 071113 */

function omapa(){
	// ! clase q maneja el mapa
	this.bounds = new google.maps.LatLngBounds();
	this.box = 'box-map';
	this.map;
	this.markers = [];
	this.styles = [];
	this.markercluster = null;
	this.mlocation = null;
	this.draw = function(){
		// * coloca el mapa en la interfaz
		var latlng_ =  new google.maps.LatLng(19.357363,-99.196871);
		var zoom_ = 5;
		google.maps.visualRefresh = true;
		var myoptions_ = {
			zoom: zoom_,
			center: latlng_,
			streetViewControl: this.isstreet,
			mapTypeControlOptions:{
				mapTypeIds: [google.maps.MapTypeId.ROADMAP,google.maps.MapTypeId.HYBRID]
			},
			panControl: true,
			panControlOptions: {
				position: google.maps.ControlPosition.RIGHT_TOP
			},
			zoomControl: true,
			zoomControlOptions: {
				style: google.maps.ZoomControlStyle.LARGE,
				position: google.maps.ControlPosition.RIGHT_TOP
			}
		};
		
		this.map = new google.maps.Map(document.getElementById(this.box), myoptions_);
		this.map.setMapTypeId(google.maps.MapTypeId.ROADMAP);
		
		// colocamos los estilos que llevaran los clusters
		this.styles = [{url: '/img/medium.png', height: 48, width: 32, anchor: [16, 0], textColor: '#ffffff', textSize: 10}, {url: '/img/hot.png', height: 48, width: 32, anchor: [16, 0], textColor: '#ffffff', textSize: 10}, {url: '/img/low.png', height: 48, width: 32, anchor: [16, 0], textColor: '#ffffff', textSize: 10}];
		
	};
	this.exists = function(){
		// regresa si encuentra el mapa
		return (this.map != '' && this.map != null && this.map != undefined) ? true : false;
	};
	this.put_marker = function(o){
		// coloca el marker en el mapa
		var punto = new google.maps.LatLng(o.lat,o.lon);
		var marker = new google.maps.Marker({
			position: punto,
			map: this.map,
			icon: '/img/low.png'
		});
		this.markers.push(marker);
		this.bounds.extend(punto);
	};
	this.put_cluster = function(){
		// colocamos el cluster de los datos
		this.markercluster = new MarkerClusterer(this.map, this.markers, {maxZoom: 18, gridSize: 20, styles: this.styles});
	};
	this.clear = function(){
		// limpiamos los markers
		for(var i = 0; i < this.markers; i++){
			this.markers[i].setMap(Null);
		}
		this.markers.length = 0;
		if(this.markercluster != null){
			this.markercluster.clearMarkers();
		}
		this.bounds = new google.maps.LatLngBounds();
	};
	this.get_location = function(){
		// ubica al usuario
		if(navigator.geolocation){
			$('#ubicacion').html('Ubicando...');
			navigator.geolocation.getCurrentPosition(put_location, error_location, {timeout:60000});
		}else{
			alert('El navegador no soporta geolocation.');
		}
	};
	this.put_location = function(lat, lon){
		// colocamos le marker de la ubicación
		var punto = new google.maps.LatLng(lat,lon);
		if(this.mlocation == null){
			this.mlocation = new google.maps.Marker({
				position: punto,
				map: this.map,
				icon: '/img/pin.png'
			});
		}else{
			this.mlocation.setPosition(punto);
		}
		this.map.setCenter(punto);
		this.map.setZoom(11);
	};
}

function put_location(geo){
	// Colocamos la ubicación del usuario.
	var lat = geo.coords.latitude;
	var lon = geo.coords.longitude;
	m__.put_location(lat, lon);
	$('#ubicacion').html('Ubicame');
}

function error_location(){
	// No se pudo obtener la ubicación
	$('#ubicacion').html('Ubicame');
	alert('No se pudo obtener la ubicación.')
}



