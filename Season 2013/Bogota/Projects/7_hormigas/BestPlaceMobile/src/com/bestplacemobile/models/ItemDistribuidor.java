package com.bestplacemobile.models;

public class ItemDistribuidor {

	private String nombreCompania;
	private String[] palabrasClaves;
	private String calificacion;
	private int numeroComentarios;
	private int numeroComentariosGoogle;

	public ItemDistribuidor(String nombreCompania, String[] palabrasClaves,
			String calificacion, int numComent, int numComentGoogle) {
		this.nombreCompania = nombreCompania;
		this.palabrasClaves = palabrasClaves;
		this.calificacion = calificacion;
		this.numeroComentarios = numComent;
		this.numeroComentariosGoogle = numComentGoogle;
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
