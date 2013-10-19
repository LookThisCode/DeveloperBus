package jellybean_logistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entities.*;
import serializer.*;

public class Persistance {
	
	private static Persistance instance = null;

	protected Persistance() {
		initialize();
	}
	
	public static Persistance getInstance() {
		if (instance == null) {
			instance = new Persistance();
		}
		return instance;
	}
	
	private void initialize() {
		
		// Initialize Gson Builder
	    GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(PlaceDeserializer.class, new PlaceDeserializer());
	    gsonBuilder.registerTypeAdapter(RouteDeserializer.class, new PlaceDeserializer());
	    gsonBuilder.registerTypeAdapter(TransportDeserializer.class, new PlaceDeserializer());
	    gson = gsonBuilder.create();
	    
	    initializeHardcode();
	}
	
	// Gson
	private Gson gson;
	
	// Home Address
	private String homeAddress;
	
	public String getHomeAddress() {
		return homeAddress;
	}
	
	// ID Transport - Position
	private HashMap<Integer, LatLng> positions = new HashMap<Integer, LatLng>();
	
	public HashMap<Integer, LatLng> getPositions() {
		return positions;
	}
	
	public LatLng getPosition(int id) {
		if (positions.containsKey(id))
			return positions.get(id);
		else
			return null;
	}
	
	public void setPosition(Integer id, double lat, double lng, boolean flagAlert) {
		
		LatLng position = positions.get(id);
		
		if (position == null) {
			position = new LatLng(lat, lng, false);
			positions.put(id, position);
		}
		else {
			position.lat = lat;
			position.lng = lng;
			position.alert = flagAlert;
		}
	}
	
	// ID Transport - Transport
	private HashMap<Integer, Transport> transports = new HashMap<Integer, Transport>();
	
	public HashMap<Integer, Transport> getTransports() {
		return transports;
	}
	
	public Transport getTransport(Integer id) {
		if (transports.containsKey(id))
			return transports.get(id);
		else
			return null;
	}
	
	// ID Transport - Route
	private HashMap<Integer, Route> routes = new HashMap<Integer, Route>();
	
	public HashMap<Integer, Route> getRoutes() {
		return routes;
	}
	
	public Route getRoute(int id) {
		if (routes.containsKey(id))
			return routes.get(id);
		else
			return null;
	}
	
	public void initializeHardcode() {
		
		Transport transport = new Transport();
		transport.Id = 1;
		transport.Name = "Jose";
		
		transports.put(transport.Id, transport);
		
		List<Place> places = new ArrayList<Place>();
		Place place = new Place();
		place.Id = 1;
		place.Name = "Cafeteria Las Maria";
		place.Description = "Para la familia, de la familia";
		place.Address = "Av Corrientes 2000, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina";
		place.Status = "notvisited";
		places.add(place);
		
		place = new Place();
		place.Id = 1;
		place.Name = "Restaurant El Trapiche";
		place.Description = "Bodegon";
		place.Address = "Av Corrientes 1000, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina";
		place.Status = "notvisited";
		places.add(place);
		
		place = new Place();
		place.Id = 1;
		place.Name = "Jugueteria Marito";
		place.Description = "De todo para los nenes";
		place.Address = "Av Corrientes 500, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina";
		place.Status = "notvisited";
		places.add(place);
		
		Route route = new Route();
		route.Trans = transport;
		route.Places = places;
		
		routes.put(transport.Id, route);
		
		setPosition(1, -34.45276, -58.86002, false);
		
		
		// Transport 2
		transport = new Transport();
		transport.Id = 2;
		transport.Name = "Roberto";
		
		transports.put(transport.Id, transport);
		
		places = new ArrayList<Place>();
		place = new Place();
		place.Id = 1;
		place.Name = "Kiosko 'El Millo'";
		place.Description = "El mas grande, lejos";
		place.Address = "Los Alamos 2600, Pilar, Buenos Aires, Argentina";
		place.Status = "notvisited";
		places.add(place);
		
		place = new Place();
		place.Id = 1;
		place.Name = "Havanna";
		place.Description = "Los mejores alfajores del mundo";
		place.Address = "Las Casuarinas 2949, Pilar, Buenos Aires, Argentina";
		place.Status = "notvisited";
		places.add(place);
		
		place = new Place();
		place.Id = 1;
		place.Name = "Vineria";
		place.Description = "La mejor calidad";
		place.Address = "Espora 3399, Pilar, Buenos Aires, Argentina";
		place.Status = "notvisited";
		places.add(place);
		
		place = new Place();
		place.Id = 1;
		place.Name = "Restaurant La Rana";
		place.Description = "Gran parrilla";
		place.Address = "San Cayetano 699, Pilar, Buenos Aires, Argentina";
		place.Status = "notvisited";
		places.add(place);

		route = new Route();
		route.Trans = transport;
		route.Places = places;
		
		routes.put(transport.Id, route);
		
		setPosition(2, -34.45276, -58.86002, false);
	}
	
}

