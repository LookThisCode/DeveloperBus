
    <div id="map-canvas"/>
    <script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA4Z4e5swq-uPOGdi7UfxLuMeKnW2t0S04&sensor=true">
    </script>
    <script type="text/javascript">
      	//var url = "trekoapp.appspot.com/json?callback=";
				//var url = "http://maps.googleapis.com/maps/api/geocode/json?address=984+Faustolo,+Sao+paulo,+SP&sensor=true";

				var locs 	= [ ["-23.5238753", "-46.6949295"], ["-23.526535", "-46.694323"] ];
				var lat 	= ["-23.5238753","-23.526535"];
				var lng 	= ["-23.5238753","-23.526535"];

function initialize() {
  var mapOptions = {
    zoom: 12,
    center: new google.maps.LatLng(-23.5489433,-46.6388182)
		
  }

  var map = new google.maps.Map(document.getElementById('map-canvas'),mapOptions);
  setMarkers(map, locais);
}


var locais = [
  ['Faustolo', -23.5238753, -46.6949295],
  ['Caio Graco', -23.526535, -46.694323],
  ['Av Paulista', -23.5630733,-46.6543584]
];


function attachSecretMessage(marker, num) {
  var message = ['<span>Hello</span>', 'is', 'the', 'secret', 'message'];
	  var infowindow = new google.maps.InfoWindow({
	    content: message[num]
	  });
	 

  google.maps.event.addListener(marker, 'click', function() {
    infowindow.open(marker.get('map'), marker);
  });
}

function drop() {
  for (var i = 0; i < locais.length; i++) {
    setTimeout(function() {
      setMarkers();
    }, i * 500);
  }
}

function setMarkers(map, locations) {
  var image = 'images/pin.png';
  for (var i = 0; i < locations.length; i++) {
    var local = locations[i];
    var myLatLng = new google.maps.LatLng(local[1], local[2]);
    var marker = new google.maps.Marker({
        position: myLatLng,
        map: map,
        icon: image,
        animation: google.maps.Animation.DROP,
        zIndex: local[1]
    });
    marker.setTitle((local[0]).toString());
    attachSecretMessage(marker, i);
  }
}

	google.maps.event.addDomListener(window, 'load', initialize);    
</script>

