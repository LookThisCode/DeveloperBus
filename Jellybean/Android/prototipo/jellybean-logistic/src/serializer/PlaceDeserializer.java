package serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import entities.Place;

public class PlaceDeserializer implements JsonDeserializer<Place> {
	
	   public Place deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
	
		JsonObject jo = (JsonObject)json;
			
		Place place = new Place();
		
		place.Id = jo.get("Id").getAsInt();
		place.Name = jo.get("Name").getAsString();
		place.Address = jo.get("Address").getAsString();
		place.Description = jo.get("Description").getAsString();	
		place.Status = jo.get("Status").getAsString();
		
		return place;
	}
	
}