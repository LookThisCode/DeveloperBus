package serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import entities.Transport;

public class TransportDeserializer implements JsonDeserializer<Transport> {
		
	@Override
	public Transport deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		
		JsonObject jo = (JsonObject)json;
				
		Transport transport = new Transport();
			
	  	transport.Id = jo.get("Id").getAsInt();
	  	transport.Name = jo.get("Name").getAsString();	
				
		return transport;
	}
		
}
