package com.sothree.slidinguppanel.demo;

public class Proveedor {

	public Proveedor(int id, String nombre, String nit, String telefono,
			String lat, String lng, String rating, String ciudad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nit = nit;
		this.telefono = telefono;
		this.lat = lat;
		this.lng = lng;
		this.rating = rating;
		this.ciudad = ciudad;
	}
	private int id;
	private String nombre;
	private String nit;
	private String telefono;
	private String lat;
	private String lng;
	private String rating;
	private String ciudad;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNit() {
		return nit;
	}
	public void setNit(String nit) {
		this.nit = nit;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	
}
