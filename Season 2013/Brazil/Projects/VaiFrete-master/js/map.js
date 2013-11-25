
  var map;
  var markers = [];


function initialize() {
  var mapOptions = {
    zoom: 12
  };
  map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);

  // Try HTML5 geolocation
  if(navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var pos = new google.maps.LatLng(position.coords.latitude,
                                       position.coords.longitude);

      var infowindow = new google.maps.InfoWindow({
        map: map,
        position: pos,
        content: 'Você está aqui!'
      });

      map.setCenter(pos);
    }, function() {
      handleNoGeolocation(true);
    });
  } else {
    // Browser doesn't support Geolocation
    handleNoGeolocation(false);
  }
}

function handleNoGeolocation(errorFlag) {
  if (errorFlag) {
    var content = 'Error: The Geolocation service failed.';
  } else {
    var content = 'Error: Your browser doesn\'t support geolocation.';
  }

  var options = {
    map: map,
    position: new google.maps.LatLng(60, 105),
    content: content
  };

  var infowindow = new google.maps.InfoWindow(options);
  map.setCenter(options.position);
}

// Add a marker to the map and push to the array.
function addMarker(location, imgUrl) {
  var marker = new google.maps.Marker({
    position: location,
    map: map,
    icon: imgUrl
  });
  markers.push(marker);
}

// Sets the map on all markers in the array.
function setAllMap(map) {
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(map);
  }
}

// Removes the markers from the map, but keeps them in the array.
function clearMarkers() {
  setAllMap(null);
}

// Shows any markers currently in the array.
function showMarkers() {
  setAllMap(map);
}

// Deletes all markers in the array by removing references to them.
function deleteMarkers() {
  clearMarkers();
  markers = [];
}


google.maps.event.addDomListener(window, 'load', initialize);


function updateMap(deliverId){
  //var timer = $.timer(/*$('#btnRefresh').click(*/function(){  
  $.get('deliverCoordinates?deliverId='+deliverId, function(data){
      console.log(data);
      deleteMarkers();
      addMarker(new google.maps.LatLng(data.src_lat, data.src_lgn), "http://www.google.com/intl/en_us/mapfiles/ms/micons/green-dot.png");
      addMarker(new google.maps.LatLng(data.des_lat, data.des_lgn), "http://www.google.com/intl/en_us/mapfiles/ms/micons/blue-dot.png");
      addMarker(new google.maps.LatLng(data.drv_lat, data.drv_lgn), "http://www.google.com/intl/en_us/mapfiles/ms/micons/red-dot.png");
      setAllMap(map);
  }).always(function() {
    console.log('finished request');
  });
}

/*
 $(window).load(function(){
  var timer = $.timer(/*$('#btnRefresh').click(function(){  
      $.get('deliverCoordinates?deliverId=5066549580791808', function(data){
        console.log(data);
        deleteMarkers();
        addMarker(new google.maps.LatLng(data.src_lat, data.src_lgn), "http://www.google.com/intl/en_us/mapfiles/ms/micons/green-dot.png");
        addMarker(new google.maps.LatLng(data.des_lat, data.des_lgn), "http://www.google.com/intl/en_us/mapfiles/ms/micons/blue-dot.png");
        addMarker(new google.maps.LatLng(data.drv_lat, data.drv_lgn), "http://www.google.com/intl/en_us/mapfiles/ms/micons/red-dot.png");
        setAllMap(map);
    }).always(function() {
      console.log('finished request');
    });
  });

  timer.set({ time : 10000, autostart : true });

});   */