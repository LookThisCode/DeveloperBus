package Controller;

import Model.User;
import java.util.Date;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

class  UserController{
	
	public static void insertUser(User user){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(user);
	}
	
}