//----------AJAX HELPER-----------
function postWithoutMessageBack(toURL, toData) {

    $.ajax({
        type: "POST",
        url: toURL,
        data: toData,
        dataType: "json",
        async: false,
        success: function(data) {
        },
        error: function(e){
            alert("Error in POST to URL: " + toURL + "- with Data: " + toData + ". e: " + JSON.stringify(e));
        }
    });

}

function postWithMessageBack(toURL, toData) {

    var message;

    $.ajax({
        type: "POST",
        url: toURL,
        data: toData,
        dataType: "json",
        async: false,
        success: function(data) {
            message = data;
        },
        error: function(e){
            alert("Error in POST to URL: " + toURL + " - with Data: " + toData + ". e: " + JSON.stringify(e));
        }
    });

    return message;
}
//--------------------------------

//----------UI SETTINGS-----------

// Colors
var colors = new Array();
colors[0] = 'red';
colors[1] = 'blue';
colors[2] = 'green';
colors[3] = 'yellow';
colors[4] = 'teal';
colors[5] = 'brown';

//--------GLOBAL VARIABLES-------

// Global Directions Service
var directionsService = new google.maps.DirectionsService();

// Global Map
var map;

// Variables
var transportMarker;
var directionsDisplayArray = new Array();
var paths = new Array();
var currentRoute;
var bounds;
var homeBounds;

// Bounds
var lat;
var lng;

// Selected Transport
var selectedTransport;
var transportRoute;
var homeAddress;
var direcitionsDisplayTemp;

// Timer
var delay = 5000;
var timer;

function start(transport, interval) {
    timer = setInterval(function(){ updatePosition(transport);}, delay);
}

function stop() {
    clearInterval(timer);
}


function renderRoute(start, end, i) {

    // Request con la nueva ruta
    var request = {
        origin: start,
        destination: end,
        travelMode: google.maps.DirectionsTravelMode.DRIVING
    };

    // Creo un renderer para la nueva ruta
    var directionsDisplay = new google.maps.DirectionsRenderer();
    directionsDisplay.setMap(map);

    if (i != null && i != 'temp'){
        var color =  colors[5 % (i+1)];
        var icon = 'res/images/markers/' + [i].toString() + '.png';

        var polylineOptions = { strokeColor: color };
        var markerOptions = { icon: icon, zIndex: i};

        var rendererOptions = { polylineOptions: polylineOptions, markerOptions: markerOptions, preserveViewport: true };
        directionsDisplay.setOptions(rendererOptions);
    }

    if (i == 'temp') {
        var rendererOptions = { suppressMarkers: true, preserveViewport: true };
        directionsDisplay.setOptions(rendererOptions);
        direcitionsDisplayTemp = directionsDisplay;
    } else {
        // Lo agrego al array para despues poder borrar las rutas sin destruir el mapa
        directionsDisplayArray[i] = directionsDisplay;
    }

    directionsService.route(request, function (response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            directionsDisplay.setDirections(response);

            // Guardo las rutas
            paths[i+1] = response.routes[0].overview_path;

            if (bounds == null)
                bounds = response.routes[0].bounds;
            else
                bounds.union(response.routes[0].bounds);

            positionViewport();
        }
    });
}

// Posicionar camara
function positionViewport()
{
    if (typeof transportMarker != 'undefined' && transportMarker != null){
        transportBounds = new google.maps.LatLngBounds(transportMarker.position);

        if (bounds == null || typeof bounds == 'undefined')
            bounds = transportBounds;
        else
            bounds.union(transportBounds);

        if (homeBounds != null) {
            if (homeBounds == null || typeof homeBounds == 'undefined')
                bounds = homeBounds;
            else
                bounds.union(homeBounds);
        }
    }

    map.fitBounds(bounds);
}

//  Get the position of the transport
function getPosition(transport) {
    var toURL = '/api/getposition';
    var toData = 'id=' + transport;
    return postWithMessageBack(toURL, toData);
}

// Set the position of the transport on the map
function setPosition(lat, lng) {
    //var image = '../war/res/images/' + getPathAlertColor();
    var image = '../res/images/' + getPathAlertColor();
    var myLatLng = new google.maps.LatLng(lat, lng);

    if (typeof transportMarker != 'undefined')
        transportMarker.setMap(null);

    transportMarker = new google.maps.Marker({
        position: myLatLng,
        map: map,
        icon: image
    });
}

function setTempRoute(start) {
    if (direcitionsDisplayTemp != null && direcitionsDisplayTemp != 'undefined')
        direcitionsDisplayTemp.setMap(null);
    /*
    var postition = { lat : paths[currentRoute][paths.length-1].lb, lng : paths[currentRoute][paths.length-1].mb };

    var myLatLng = new google.maps.LatLng(position.lat, position.lng);
    */
    renderRoute(start, paths[currentRoute][0], 'temp')
}

function updatePosition(transport){

    var position = getPosition(transport);
    setPosition(position.lat, position.lng);

    if (position.alert == true) {
        $('#alert').show();
    } else  {
        $('#alert').hide();
    }

    if (typeof paths != 'undefined' && paths.length != 0) {
        var myLatLng = new google.maps.LatLng(position.lat, position.lng);
        setTempRoute(myLatLng);
    }

    positionViewport();
}

function getPathAlertColor(){

    if (typeof paths == 'undefined' || paths.length == 0)
        return 'icon-green.png';

    if (typeof currentRoute == 'undefined' || currentRoute == 0)
        return 'icon-green.png';


    var position = { lat : transportMarker.position.lb, lng : transportMarker.position.mb };
    var dist = distance(paths[currentRoute], position);

    if (dist < 200)
        return 'icon-green.png';
    else if (200 < dist && dist < 600)
        return 'icon-orange.png';
    else if (dist >= 600)
        return 'icon-red.png';

    return 'icon-green.png';
}

// Get routes of a transport
function getRoute(transport) {
    var toURL = 'api/getroute';
    var toData = 'id=' + transport;
    return postWithMessageBack(toURL, toData)
}

function processPlaces(places) {

    var length = places.length;

    var segments = new Array();

    segments[0] = { start: homeAddress, end: places[0].Address}

    for (var i = 0; i < length; i++) {

        if (i >= length - 1)
            break;

        segments[i+1] = { start: places[i].Address, end: places[i+1].Address};
    }

    return segments;
}

// Set routes on the map
function setRoutes(segments) {

    clearRoutes();

    var length = segments.length,
        route = null;

    for (var i = 0; i < length; i++) {
        route = segments[i];
        renderRoute(route.start, route.end, i)
    }
}

// Get the home position
function getHomePosition(){
    var toURL = '/api/gethomeposition';
    var toData = '';
    var msg = postWithMessageBack(toURL, toData);
    homeAddress = msg.address;
    getGeoCoordinates(msg.address);
}

// Get Coordinates and set de home position
function  getGeoCoordinates(address) {
    var geocoder = new google.maps.Geocoder();
    geocoder.geocode( { 'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            setHomePosition(results[0].geometry.location.lb, results[0].geometry.location.mb);
        } else {
            alert("No se pudo encontrar las coordenadas de la direccion: " + status);
        }
    });
}

// Set home position
function setHomePosition(lat, lng){

    //var image = '../war/res/images/home';
    var image = '../res/favicon.png';
    var myLatLng = new google.maps.LatLng(lat, lng);
    transportMarker = new google.maps.Marker({
        position: myLatLng,
        map: map,
        icon: image
    });

    homeBounds = new google.maps.LatLngBounds(myLatLng);
}

// Select a transport to show
function selectTransport(transport)
{
    // Limpio el mapa de rutas
    clearMap();
}

// Change Up the priority of the route
function moveRouteUp(transport, route){

}

// Change Down the priority of the route
function moveRouteDown(transport, route){

}

// Delete a route
function deleteRoute(transport, route){

}

// Add a route
function addRoute(transport, route){

}

// Clean Map
function cleanMap() {
    // Coordenadas de Buenos Aires
    var bsas = new google.maps.LatLng(-34.5965, -58.382);

    var mapOptions = {
        zoom: 12,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        center: bsas
    }

    // Creo el mapa global
    map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

    initializeVariables();
}

function clearRoutes() {
    var length = directionsDisplayArray.length;
    for(var i = 0; i < length; i++) {
        directionsDisplayArray[i].setMap(null);
    }

    directionsDisplayArray = new Array();
}

function initializeVariables() {
    directionsDisplayArray = new Array();
    paths = new Array();
    currentRoute = 0;
    bounds = null;
    selectedTransport = null;
    transportRoute = null;
    homeAddress = null;
    direcitionsDisplayTemp = null;
    cleanTable();
    clearRoutes();
}

function distance(path, position) {
    var dist = bdccGeoDistanceToPolyMtrs(path, position);
    return dist;
}
function changeTransport() {
    stop();

    if (typeof homeAddress == 'undefined' || homeAddress == null ) {
        getHomePosition(changeTransport);
    }

    if (typeof homeAddress == 'undefined' || homeAddress == null ) {
        alert('Falta la direccion Home');
    }

    bounds = null;

    selectedTransport = $('#lstTransports').val();
    var route = getRoute(selectedTransport);
    displayRouteTable(route);
    setCurrentRoute(route);
    transportRoute = route;
    var segments = processPlaces(route.Places);
    setRoutes(segments);

    start(selectedTransport);
}

function displayRouteTable(route) {

    cleanTable();

    $.each(route.Places, function( index, value ) {
        displayRow(value);
    });

}

function cleanTable() {
    $('#tableRoutes tr').remove();
}

function displayRow(place) {

    var tr = '<tr class="' + place.Status + '">';
    var row = '<td class="main"><p><strong>' + place.Name + '</strong><br/>' + place.Address + '<br/>' + place.Description + '</p></td>';
    var abmRow = '<td class="abm"><a href=""><img src="res/images/arrowskipup.png" width="24px"></a><br/><a href=""><img src="res/images/remove.png" width="24px"></a><br/><a href=""><img src="res/images/arrowskipdown.png" width="24px"></a></td></tr>';

    $('#tableRoutes').append(tr + row + abmRow);
}

function setCurrentRoute(route) {

    if (typeof route == 'undefined') {
        currentRoute = 0;
        return;
    }

    $.each(route.Places, function( index, value ) {
        if (value.Status == 'notvisited') {
            currentRoute = index + 1;
            return false;
        }
    });
}

// Tester
function tester() {

    var toURL = '/api/getroute';
    var toData = 'id=1';

    var message = postWithMessageBack(toURL, toData);

    alert(JSON.stringify(message));
}

function testerUpdatePosition() {
    setInterval(function(){ updatePosition('1');},10000);
}