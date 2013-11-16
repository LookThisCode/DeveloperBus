package com.bestplacemobile.models;

public class ItemDistribuidor {

	private int id;
	private String NIT;
	private String nombreCompania;
	private String nombreRepLegal;
	private String urlLogo;
	private String calificacion;
	private int numeroComentarios;
	private int numeroComentariosGoogle;	
	private String[] palabrasClaves;

	public ItemDistribuidor(int id, String nombreCompania,
			String[] palabrasClaves, String calificacion, int numComent,
			int numComentGoogle) {
		this.id = id;
		this.nombreCompania = nombreCompania;
		this.palabrasClaves = palabrasClaves;
		this.calificacion = calificacion;
		this.numeroComentarios = numComent;
		this.numeroComentariosGoogle = numComentGoogle;
	}

	public String getNombreRepLegal() {
		return nombreRepLegal;
	}

	public void setNombreRepLegal(String nombreRepLegal) {
		this.nombreRepLegal = nombreRepLegal;
	}

	public String getUrlLogo() {
		return urlLogo;
	}

	public void setUrlLogo(String urlLogo) {
		this.urlLogo = urlLogo;
	}

	public String getNIT() {
		return NIT;
	}

	public void setNIT(String nIT) {
		NIT = nIT;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ItemDistribuidor() {
	}

	public String[] getPalabrasClaves() {
		return palabrasClaves;
	}

	public void setPalabrasClaves(String[] palabrasClaves) {
		this.palabrasClaves = palabrasClaves;
	}

	public int getNumeroComentariosGoogle() {
		return numeroComentariosGoogle;
	}

	public void setNumeroComentariosGoogle(int numeroComentariosGoogle) {
		this.numeroComentariosGoogle = numeroComentariosGoogle;
	}

	public String getNombreCompania() {
		return nombreCompania;
	}

	public void setNombreCompania(String nombreCompania) {
		this.nombreCompania = nombreCompania;
	}

	public String[] getPalabarasClaves() {
		return palabrasClaves;
	}

	public void setPalabarasClaves(String[] palabarasClaves) {
		this.palabrasClaves = palabarasClaves;
	}

	public String getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}

	public int getNumeroComentarios() {
		return numeroComentarios;
	}

	public void setNumeroComentarios(int numeroComentarios) {
		this.numeroComentarios = numeroComentarios;
	}

}
