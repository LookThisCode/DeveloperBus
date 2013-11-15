package co.com.challenge.rest.model;

/**
 * Modelo de Ciudades
 * @author rachirib@gmail.com
 * @project sample-rest
 * @class CiudadDTO
 * @date 14/11/2013
 *
 */
public class CiudadDeveloperBusDTO {
	
	private String id;
	private String nombre;
	
	
	public CiudadDeveloperBusDTO(String id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

}
