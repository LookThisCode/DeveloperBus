
google.maps.event.addDomListener(window, 'load', function initialize() {
    navigator.geolocation.getCurrentPosition(function (position) {
        var myLatlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
        var map = new google.maps.Map(document.getElementById("home-map"),
            {
              center: myLatlng,
              zoom: 15,
              mapTypeId: google.maps.MapTypeId.ROADMAP,
              streetViewControl: false,
              styles: [
                 {
                    stylers: [
                      { hue: "#0093d0" },
                      { saturation: -70 },
                      { lightness: 70 }
                    ]
                  },{
                    featureType: "road",
                    elementType: "geometry",
                    stylers: [
                      { lightness: 100 },
                      { visibility: "simplified" }
                    ]
                  },{
                    featureType: "road",
                    elementType: "labels",
                    stylers: [
                      { visibility: "off" }
                    ]
                  }
              ]
            });

        var marker = new google.maps.Marker({
              position: myLatlng,
              map: map,
              title: 'You are here!'
          });
    });
});
