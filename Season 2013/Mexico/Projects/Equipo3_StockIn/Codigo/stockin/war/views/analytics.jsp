<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>

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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/start.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/start.menu.css" type="text/css">
  <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
  <script type="text/javascript">
  	$(function() {
	    $( "#tabs" ).tabs();
	  });
  </script>
  
  <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load('visualization', '1', {'packages':['motionchart']});
      google.setOnLoadCallback(drawChart);
      google.setOnLoadCallback(drawChart2);
      function drawChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Frutas');
        data.addColumn('date', 'Fecha');
        data.addColumn('number', 'Ventas');
        data.addColumn('number', 'Gastos');
        data.addColumn('string', 'Estado');
        data.addRows([
          ['JIMADOR, REPOSADO, BOTELLA DE 700 ML',  new Date (2013,0,1), 1000, 300, 'DF'],
          ['CABRITO, REPOSADO, BOTELLA DE 1 LT', new Date (2013,0,1), 1150, 200, 'EDO MEX'],
          ['100 AÑOS, BLANCO, BOTELLA DE 750 ML', new Date (2013,0,1), 300,  250, 'EDO MEX'],
          ['JIMADOR, REPOSADO, BOTELLA DE 700 ML',  new Date (2013,6,1), 1200, 400, 'DF'],
          ['JIMADOR, REPOSADO, BOTELLA DE 700 ML', new Date (2013,6,1), 750,  150, 'DF'],
          ['CABRITO, REPOSADO, BOTELLA DE 1 LT',  new Date (2013,0,1), 1400, 400, 'EDO MEX'],
          ['ORENDAIN, BLANCO, BOTELLA DE 1 LT', new Date (2013,6,1), 788,  617, 'EDO MEX']
        ]);
        var chart = new google.visualization.MotionChart(document.getElementById('chart_div'));
        chart.draw(data, {width: 850, height:400});
      }
      function drawChart2() {
          var data = new google.visualization.DataTable();
          data.addColumn('string', 'Frutas');
          data.addColumn('date', 'Fecha');
          data.addColumn('number', 'Ventas');
          data.addColumn('number', 'Gastos');
          data.addColumn('string', 'Estado');
          data.addRows([
		['JIMADOR, REPOSADO, BOTELLA DE 700 ML',  new Date (2013,0,1), 1000, 300, 'DF'],
		['CABRITO, REPOSADO, BOTELLA DE 1 LT', new Date (2013,0,1), 1150, 200, 'EDO MEX'],
		['100 AÑOS, BLANCO, BOTELLA DE 750 ML', new Date (2013,0,1), 300,  250, 'EDO MEX'],
		['DEL HOGAR, TOTOPOS, DE MAIZ, BOLSA DE 280 GR',  new Date (2013,6,1), 1200, 400, 'DF'],
		['JIMADOR, REPOSADO, BOTELLA DE 700 ML', new Date (2013,6,1), 750,  150, 'DF'],
		['CABRITO, REPOSADO, BOTELLA DE 1 LT',  new Date (2013,0,1), 1400, 400, 'EDO MEX'],
		['ORENDAIN, BLANCO, BOTELLA DE 1 LT', new Date (2013,6,1), 788,  617, 'EDO MEX'],
		['DEL HOGAR, TOTOPOS, DE MAIZ, BOLSA DE 280 GR', new Date (2013,6,1), 788,  617, 'CUERNAVACA'],
		['SANISSIMO, TOSTADAS, HORNEADAS, PAQ DE 220 GR', new Date (2013,6,1), 788,  617, 'TOLUCA'],
		['LA SIERRA, REFRITOS, CLAROS, LATA DE 440 GR.', new Date (2013,6,1), 788,  617, 'EDO MEX'],
		['CAMPEON, ALIM SECO P/PERRO, CARNE, CEREAL, VEG Y LECHE, 2 KG', new Date (2013,6,1), 788,  617, 'TOLUCA'],
		['HUGGIES, GRANDE , ULTRA CONFORT, ETAPA 4, PAQ DE 36 PZAS',  new Date (2013,0,1), 1000, 300, 'DF'],
		['CABRITO, REPOSADO, BOTELLA DE 1 LT', new Date (2013,0,1), 1150, 200, 'EDO MEX'],
		['100 AÑOS, BLANCO, BOTELLA DE 750 ML', new Date (2013,0,1), 300,  250, 'EDO MEX'],
		['JIMADOR, REPOSADO, BOTELLA DE 700 ML',  new Date (2013,6,1), 1200, 400, 'DF'],
		['HUGGIES, GRANDE , ULTRA CONFORT, ETAPA 4, PAQ DE 36 PZAS', new Date (2013,6,1), 750,  150, 'DF'],
		['CABRITO, REPOSADO, BOTELLA DE 1 LT',  new Date (2013,0,1), 1400, 400, 'TOLUCA'],
		['SANISSIMO, TOSTADAS, HORNEADAS, PAQ DE 220 GR', new Date (2013,6,1), 788,  617, 'EDO MEX']
          ]);
          var chart = new google.visualization.MotionChart(document.getElementById('chart_div2'));
          chart.draw(data, {width: 850, height:400});
        }
  </script>
<title>Análisis</title>
<style type="text/css">

#tabsholder {
    border: 2px solid rgb(251, 194, 0);
    border-radius: 5px;
    width: 900px;
    margin-left: auto;
    margin-right: auto;
    padding: 8px;
}

</style>
</head>
<body>
	<%
    UserService userService = UserServiceFactory.getUserService();
%>
	<div id='cssmenu'>
		<ul>
		   <li class='has-sub'><a href='#'><span>${sessionScope.nickname}</span></a>
		      <ul>
		         <li><a href="/"><span>Inicio</span></a></li>
		         <li><a href="<%= userService.createLogoutURL("/") %>"><span>Salir</span></a></li>
		      </ul>
		   </li>
		</ul>
	</div>
	
	<div id="headerlogo"><span style="vertical-align: sub;">STOCK-IN&nbsp;</span>
		<img src="/img/logo.png" width="30" height="30" border="0" style="float: right; margin-top: 3px; margin-right: 2px;">
	</div><br />
<br /><br />

<div class="card analisis" style="position: absolute;">
	<img alt="analisis" src="${pageContext.request.contextPath}/img/analisis-icon.png"><br />
	<span>Análisis</span>
</div>

<div id="tabsholder">
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1">Alertas</a></li>
	    <li><a href="#tabs-2">Brand A</a></li>
	    <li><a href="#tabs-3">Brand B</a></li>
	  </ul>
	  <div id="tabs-1">
	    <div id="map-canvas" style="width: 850px; height: 400px; margin: auto;"></div>
	  </div>
	  <div id="tabs-2">
	    <div id="chart_div" style="width: 850px; height: 400px; margin: auto;"></div>
	  </div>
	  <div id="tabs-3">
	    <div id="chart_div2" style="width: 850px; height: 400px; margin: auto;"></div>
	  </div>
	</div>
</div>

</body>
</html>