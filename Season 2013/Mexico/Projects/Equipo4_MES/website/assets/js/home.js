
google.maps.event.addDomListener(window, 'load', function initialize() {
    navigator.geolocation.getCurrentPosition(function (position) {
        var myLatlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
        var map = new google.maps.Map(document.getElementById("home-map"),
            {
              disableDefaultUI: true,
              draggable:false,
              center: myLatlng,
              zoom: 15,
              mapTypeId: google.maps.MapTypeId.ROADMAP,
              streetViewControl: false,
              styles: [
                 {
                    stylers: [
                      { hue: "#00ffe6" },
                      { saturation: -20 }
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

var signInCallback = function (result) {
        if (result['error']) {
        } else {
            $('#code').attr('value', result['code']);
            $('#at').attr('value', result['access_token']);
            $('#google-plus').submit();
        }
    };