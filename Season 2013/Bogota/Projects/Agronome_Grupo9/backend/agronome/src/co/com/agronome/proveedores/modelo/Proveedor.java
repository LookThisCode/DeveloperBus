package co.com.agronome.proveedores.modelo;

import java.util.ArrayList;
import java.util.List;

import co.com.agronome.proveedores.servlet.Util;
import co.com.agronome.proveedores.spi.model.ProveedorDTO;

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
	 * @param latitud TODO
	 * @param longitud TODO
	 * @param rate
	 */
	public static void createOrUpdateProveedor(String usuario,  String pass, String nit, String nombre, String etiquetas, String telefono, String latitud, String longitud){
		Entity proveedor = getSingleProveedor(usuario);
		if (proveedor == null) {
			proveedor = new Entity("Proveedor", usuario);
			proveedor.setProperty("usuario", usuario);
			proveedor.setProperty("pass", pass);
			proveedor.setProperty("nit", nit);
			proveedor.setProperty("nombre", nombre);
			proveedor.setProperty("telefono", telefono);
			proveedor.setProperty("etiquetas", etiquetas);
			proveedor.setProperty("latitud", latitud);
			proveedor.setProperty("longitud", longitud);
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
	 * Crear un Proveedor
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @param usuario
	 * @param pass
	 * @param nit
	 * @param nombre
	 * @param etiquetas
	 * @param telefono
	 * @throws Exception 
	 */
	public static void createProveedor(String usuario, String pass, String nit,
			String nombre, String etiquetas, String telefono, String latitud,
			String longitud) throws Exception {
		Entity proveedor = getSingleProveedor(usuario);
		if (proveedor == null) {
			proveedor = new Entity("Proveedor", usuario);
			proveedor.setProperty("usuario", usuario);
			proveedor.setProperty("pass", pass);
			proveedor.setProperty("nit", nit);
			proveedor.setProperty("nombre", nombre);
			proveedor.setProperty("telefono", telefono);
			proveedor.setProperty("etiquetas", etiquetas);
			proveedor.setProperty("latitud", latitud);
			proveedor.setProperty("longitud", longitud);
			proveedor.setProperty("rate", 0);
			Util.persistEntity(proveedor);
		}else{
			throw new Exception("El usuario ya existe");
		}
	}
	/**
	 * Actualizar un Proveedor
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @param usuario
	 * @param pass
	 * @param nit
	 * @param nombre
	 * @param etiquetas
	 * @param telefono
	 * @throws Exception 
	 */
	public static void actualizarProveedor(String usuario, String pass,
			String nit, String nombre, String etiquetas, String telefono,
			String latitud, String longitud) throws Exception {
		Entity proveedor = getSingleProveedor(usuario);
		if (proveedor != null) {
			proveedor.setProperty("nit", nit);
			proveedor.setProperty("nombre", nombre);
			proveedor.setProperty("telefono", telefono);
			proveedor.setProperty("etiquetas", etiquetas);
			proveedor.setProperty("latitud", latitud);
			proveedor.setProperty("longitud", longitud);
			Util.persistEntity(proveedor);
		}else{
			throw new Exception("El usuario ya existe");
		}
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
	 * Consultar proveedores por user
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @param proveedorName
	 * @return
	 */
	public static Iterable<Entity> getProveedor(String user) {
		Iterable<Entity> entities = Util.listEntities("Proveedor", "usuario",	user);
		return entities;
	}
	/**
	 * Obtener un Proveedor
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @param nit
	 * @return
	 */
	public static Entity getSingleProveedor(String usuario) {
		Key key = KeyFactory.createKey("Proveedor", usuario);
		return Util.findEntity(key);
	}
	/**
	 * Validar un Prooveedor
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @return
	 */
	
	public static Entity validateProveedor(String usuario, String pass){
		Key key = KeyFactory.createKey("Proveedor", usuario);
		Entity entity = Util.findEntity(key);
		
		if (entity == null) {
			return null;
		}
		
		String entityPass = (String) entity.getProperty("pass");
		if (entityPass.equalsIgnoreCase(pass)) {
			return entity;
		}else
			return null;
	}
	
	/**
	 * Consulta de los proveedores para exponer en el API
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 16/11/2013
	 * @param tagName
	 * @return
	 */
	public static List<ProveedorDTO> consultarProveedoresAPI(String tagName){
		Iterable<Entity> allProveedores = Proveedor.getAllProveedores();
		List<ProveedorDTO> lista = new ArrayList<ProveedorDTO>();
		for (Entity entity : allProveedores) {
			ProveedorDTO dto  = new ProveedorDTO();
			mapEntityProveedor(entity, dto);
			lista.add(dto);
		}
		return lista;
	}

	/**
	 * Mapeo de Entitidad de Entidades a Proveedor
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 16/11/2013
	 * @param entity
	 * @param dto
	 */
	private static void mapEntityProveedor(Entity entity, ProveedorDTO dto) {
		dto.setUsuario((String) entity.getProperty("usuario"));
		dto.setNit((String) entity.getProperty("nit"));
		dto.setNombre((String) entity.getProperty("nombre"));
		dto.setTelefono((String) entity.getProperty("telefono"));
		dto.setLatitud((String) entity.getProperty("latitud"));
		dto.setLongitud((String) entity.getProperty("longitud"));
		Long valueLong = (Long) entity.getProperty("rate");
		dto.setRating(valueLong != null ? valueLong.toString():null);
	}
	
	/**
	 * Consultar Proveedor por Usuario
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 16/11/2013
	 * @param usuario
	 * @return
	 */
	public static ProveedorDTO consultarProveedorPorUsuario(String usuario){
		Entity entity = Proveedor.getSingleProveedor(usuario);
		if (entity == null) {
			return null;
		}
		ProveedorDTO dto = new ProveedorDTO();
		mapEntityProveedor(entity, dto);
		return dto;
	}
}
