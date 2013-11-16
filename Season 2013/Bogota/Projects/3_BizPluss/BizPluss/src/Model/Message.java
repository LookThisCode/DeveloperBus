package Model;

import com.google.gson.annotations.Expose;
import Model.Jsonifiable;

public class Message extends Jsonifiable {


  public Message(String message) {
	  this.message = message;
  }
  
  public String message;

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}
  
  

}

