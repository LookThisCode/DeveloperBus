package mx.stockin.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Controller
public class MainController {
	@RequestMapping("start")
	public String showStartMenu(){
		return "start";
	}
	
	@RequestMapping(value="analytics", method=RequestMethod.GET)
	public String showAnalytics(ModelMap model){
		
		return "analytics";
	}
	
	@RequestMapping(value="products", method=RequestMethod.GET)
	public String showProducts(ModelMap model){
		
		model.put("products", getProducts());

		List<Entity> cats = getCatalogs();
		List<String> catList = new ArrayList<String>();
		
		for(Entity cat : cats){
			catList.add(cat.getProperty("cname").toString());
		}
		
		model.put("cats", catList);
		
		return "products";
	}
	
	@RequestMapping(value="products", method=RequestMethod.POST)
	public String saveProducts(ModelMap model, @RequestParam("producto")String nombre,
			@RequestParam("catalogo")String catalogo, @RequestParam("content")String content){
		
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        String productName = nombre;
        Key catalogueKey = KeyFactory.createKey("Product", productName);
        String description = content;
        Date date = new Date();      
        
        Entity product = new Entity("Product", catalogueKey);
        product.setProperty("user", user);
        product.setProperty("date", date);
        product.setProperty("cname", productName);
        product.setProperty("catalog", catalogo);
        product.setProperty("description", description);
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(product);
		
		model.put("message", "Producto guardado.");
		
		model.put("products", getProducts());
		
		List<Entity> cats = getCatalogs();
		List<String> catList = new ArrayList<String>();
		
		for(Entity cat : cats){
			catList.add(cat.getProperty("cname").toString());
		}
		
		model.put("cats", catList);
		
		return "products";
	}
	
	@RequestMapping(value="services", method=RequestMethod.GET)
	public String showServices(ModelMap model){
		
		model.put("products", getProducts());

		List<Entity> cats = getCatalogs();
		List<String> catList = new ArrayList<String>();
		
		for(Entity cat : cats){
			catList.add(cat.getProperty("cname").toString());
		}
		
		model.put("cats", catList);
		
		return "services";
	}
	
	@RequestMapping(value="services", method=RequestMethod.POST)
	public String saveServices(ModelMap model, @RequestParam("producto")String nombre,
			@RequestParam("catalogo")String catalogo, @RequestParam("content")String content){
		
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        String productName = nombre;
        Key catalogueKey = KeyFactory.createKey("Product", productName);
        String description = content;
        Date date = new Date();      
        
        Entity product = new Entity("Product", catalogueKey);
        product.setProperty("user", user);
        product.setProperty("date", date);
        product.setProperty("cname", productName);
        product.setProperty("catalog", catalogo);
        product.setProperty("description", description);
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(product);
		
		model.put("message", "Producto guardado.");
		
		model.put("products", getProducts());
		
		List<Entity> cats = getCatalogs();
		List<String> catList = new ArrayList<String>();
		
		for(Entity cat : cats){
			catList.add(cat.getProperty("cname").toString());
		}
		
		model.put("cats", catList);
		
		return "services";
	}
	
	@RequestMapping(value="catalogs", method=RequestMethod.GET)
	public String showCatalogs(ModelMap model){
		
        model.put("catalogos", getCatalogs());
		
		return "catalogs";
	}
	
	private List<Entity> getCatalogs(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    //Key catalogueKey = KeyFactory.createKey("Catalogue",100);
	    // Run an ancestor query to ensure we see the most up-to-date
	    // view of the Greetings belonging to the selected Guestbook.
	    Query query = new Query("Catalogue").addSort("date", Query.SortDirection.DESCENDING);
	    List<Entity> catalogues = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
	    
	    return catalogues;
	}
	
	private List<Entity> getProducts(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    //Key catalogueKey = KeyFactory.createKey("Catalogue",100);
	    // Run an ancestor query to ensure we see the most up-to-date
	    // view of the Greetings belonging to the selected Guestbook.
	    Query query = new Query("Product").addSort("date", Query.SortDirection.DESCENDING);
	    List<Entity> catalogues = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
	    
	    return catalogues;
	}
	
	@RequestMapping(value="catalogs", method=RequestMethod.POST)
	public String saveCatalogs(ModelMap model, @RequestParam("nombre")String nombre,
			@RequestParam("categoria")String categorias, @RequestParam("content")String content){
		
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        String catalogueName = nombre;
        Key catalogueKey = KeyFactory.createKey("Catalogue", catalogueName);
        String description = content;
        String category = categorias;
        Date date = new Date();      
        
        Entity catalogue = new Entity("Catalogue", catalogueKey);
        catalogue.setProperty("user", user);
        catalogue.setProperty("date", date);
        catalogue.setProperty("cname", catalogueName);
        catalogue.setProperty("category", category);
        catalogue.setProperty("description", description);
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(catalogue);
        
        model.put("catalogos", getCatalogs());
		model.put("message", "Catálogo guardado.");
		
		return "catalogs";
	}
	
	@RequestMapping("services")
	public String showServices(){
		return "stockin";
	}
	
}
