package com.bestplacemobile.models;

public class ItemImagenDetalladaDist {

	private String descripcion;
	private String nombre;
	private String longitud;
	private String latitud;

	public ItemImagenDetalladaDist(String descripcion, String nombre,
			String longitud, String latitud) {
		super();
		this.descripcion = descripcion;
		this.nombre = nombre;
		this.longitud = longitud;
		this.latitud = latitud;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

}
