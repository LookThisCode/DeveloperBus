package serializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import entities.*;

public class RouteDeserializer implements JsonDeserializer<Route> {
	
	   @Override
	   public Route deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
	
		JsonObject jo = (JsonObject)json;
			
		Route route = new Route();
		
		Gson gson = new Gson();
		
		String placesJson = jo.get("Places").getAsString();		
		List<Place> places = gson.fromJson(placesJson, new TypeToken<List<Place>>(){}.getType());
		
		String transportJson = jo.get("Trans").getAsString();
		Transport transport = gson.fromJson(transportJson, new TypeToken<List<Transport>>(){}.getType());
				
		
		route.Trans = transport;
		route.Places = places;
		
		return route;
	}
	
}