function load(lat,long,level) {
      if (GBrowserIsCompatible()) {
        var map = new GMap2(document.getElementById("map_container"));
        map.setCenter(new GLatLng(lat,long), level);
        map.addControl(new GMapTypeControl());
        map.addControl(new GLargeMapControl());
        map.addControl(new GScaleControl());
        map.addControl(new GOverviewMapControl());
        map.setMapType(G_HYBRID_TYPE);
        var point = new GLatLng(lat,long);
        map.addOverlay(new GMarker(point));    
}
      
function addtag(lat,long,name, address,url) {
	 if (GBrowserIsCompatible()) {
	        var map = new GMap2(document.getElementById("map_container"));
	        var marker = new GMarker(point);
	        		GEvent.addListener(marker, "click", function() { 
	        		marker.openInfoWindowHtml(address); } );
	          		return marker;
	          }
	          var point = new GLatLng(lat,long);
	          var address = '<b>'.name.'</b><br/><i>'.addess.'</i><br /><a href="'.url.'">'.name.'</a>';
	          var marker = addtag(point, address);
	         map.addOverlay(marker);
	 }
}