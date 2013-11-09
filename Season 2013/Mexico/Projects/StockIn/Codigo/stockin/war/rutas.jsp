

<!DOCTYPE html>
<html>
  <head>
    <title>Getting properties with event handlers</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <style>
      html, body, #map-canvas {
        height: 100%;
        margin: 0px;
        padding: 0px
      }
    </style>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
    <script>
function initialize() {
  var myLatLng = new google.maps.LatLng(28.6744361, -106.0778904);
  var mapOptions = {
    zoom: 15,
    center: myLatLng,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };

  var map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);

  var infowindow = new google.maps.InfoWindow({
    content: '<b>Alerta:</b> Bajo nivel de producto',
    position: myLatLng
  });
  infowindow.open(map);

  google.maps.event.addListener(map, 'zoom_changed', function() {
    var zoomLevel = map.getZoom();
    map.setCenter(myLatLng);
    infowindow.setContent('Zoom: ' + zoomLevel);
  });
}

google.maps.event.addDomListener(window, 'load', initialize);

    </script>
  </head>
  <body>
    <div id="map-canvas" style="width: 500px; height: 300px"></div>
  </body>
</html>

