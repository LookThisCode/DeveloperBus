package co.com.agronome.proveedores.modelo;

import co.com.agronome.proveedores.servlet.Util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * Clase CRUD de Proveedor
 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
 * @project agronome
 * @class Proveedor
 * @date 15/11/2013
 *
 */
public class Proveedor {


	/**
	 * Clase para crear y actualizar un proveedor
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @param usuario TODO
	 * @param pass TODO
	 * @param nombre
	 * @param etiquetas
	 * @param rate
	 */
	public static void createOrUpdateProveedor(String usuario,  String pass, String nit, String nombre, String etiquetas, String telefono){
		Entity proveedor = getSingleProveedor(usuario,pass);
		if (proveedor == null) {
			proveedor = new Entity("Proveedor", usuario+"/"+pass);
			proveedor.setProperty("usuario", usuario);
			proveedor.setProperty("pass", pass);
			proveedor.setProperty("nit", nit);
			proveedor.setProperty("nombre", nombre);
			proveedor.setProperty("telefono", telefono);
			proveedor.setProperty("etiquetas", etiquetas);
		} else {
			if (nombre != null && !"".equals(nombre)) {
				proveedor.setProperty("nombre", nombre);
			}
			if (telefono != null && !"".equals(telefono)) {
				proveedor.setProperty("telefono", telefono);
			}
			if (etiquetas != null && !"".equals(nombre)) {
				proveedor.setProperty("etiquetas", etiquetas);
			}
		}
		Util.persistEntity(proveedor);
	}
	

	/**
	 * Consultar todos los proveedores 
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @return
	 */
	public static Iterable<Entity> getAllProveedores() {
		Iterable<Entity> entities = Util.listEntities("Proveedor", null, null);
		return entities;
	}

	/**
	 * Consultar proveedores por nit
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @param proveedorName
	 * @return
	 */
	public static Iterable<Entity> getProveedor(String nit) {
		Iterable<Entity> entities = Util.listEntities("Proveedor", "nit",	nit);
		return entities;
	}
	/**
	 * Obtener un Proveedor
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @param nit
	 * @return
	 */
	public static Entity getSingleProveedor(String usuario, String pass) {
		Key key = KeyFactory.createKey("Proveedor", usuario+'/'+pass);
		return Util.findEntity(key);
	}
}
