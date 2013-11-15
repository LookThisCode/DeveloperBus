
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <title>Google Maps JavaScript API Example: Marker Drag Events</title>
    <script src="//maps.google.com/maps?file=api&amp;v=2&amp;key=AIzaSyC2XFqtxmDCQ17u9-EADmKAxe2VC7TlDx4"
            type="text/javascript"></script>
    <script type="text/javascript">

    function initialize() {
      if (GBrowserIsCompatible()) {
        var map = new GMap2(document.getElementById("map_canvas"));
        var center = new GLatLng(28.6744361, -106.0778904);
        map.setCenter(center, 13);
        

        var marker = new GMarker(center, {draggable: true});

        GEvent.addListener(marker, "dragstart", function() {
          map.closeInfoWindow();
        });

        GEvent.addListener(marker, "dragend", function() {
          marker.openInfoWindowHtml("Just bouncing along...");
        });

        map.addOverlay(marker);

      }
    }
    </script>
  </head>

  <body onload="initialize()" onunload="GUnload()">
    <div id="map_canvas" style="width: 500px; height: 300px"></div>
  </body>
</html>
