package co.com.agronome.proveedores.spi.model;

/**
 * 
 * Proveedor Data Transfer Object
 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
 * @project agronome
 * @class ProveedorDTO
 * @date 15/11/2013
 *
 */
public class ProveedorDTO {
	
	private String usuario;
	private String nit;
	private String nombre;
	private String telefono;
	private String latitud;
	private String longitud;
	private String rating;
	private String ciudad;
	
	/**
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}
	/**
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @param rating the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}
	/**
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}
	/**
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	/**
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @return the nit
	 */
	public String getNit() {
		return nit;
	}
	/**
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @param nit the nit to set
	 */
	public void setNit(String nit) {
		this.nit = nit;
	}
	/**
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	/**
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @return the latitud
	 */
	public String getLatitud() {
		return latitud;
	}
	/**
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @param latitud the latitud to set
	 */
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	/**
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @return the longitud
	 */
	public String getLongitud() {
		return longitud;
	}
	/**
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @param longitud the longitud to set
	 */
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	
	/**
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 16/11/2013
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 16/11/2013
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
}
