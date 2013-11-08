/*! js Hot it | Objeto Mapa | 071113 */

function omapa(){
	// ! clase q maneja el mapa
	this.extent = null;
	this.box = 'box-map';
	this.map;
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
	};
	this.exists = function(){
		// regresa si encuentra el mapa
		return (this.map != '' && this.map != null && this.map != undefined) ? true : false;
	};
	this.setextent = function(extent){
		// ! ajusta el nivel de zoom al extent 
		var v = extent.split(',');
		var southwest = new google.maps.LatLng(v[1],v[0]);
		var northeast = new google.maps.LatLng(v[3],v[2]);
		var bounds = new google.maps.LatLngBounds(southwest, northeast);
		this.map.fitBounds(bounds);
	};
}
