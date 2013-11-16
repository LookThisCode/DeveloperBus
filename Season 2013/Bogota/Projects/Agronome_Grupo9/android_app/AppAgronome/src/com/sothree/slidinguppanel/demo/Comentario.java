package com.sothree.slidinguppanel.demo;

public class Comentario {
	
	public Comentario(String usuario, String comentario, int rating) {
		super();
		this.usuario = usuario;
		this.comentario = comentario;
		this.rating = rating;
	}
	private String usuario;
	private String comentario;
	private int rating;
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	
	
	

}
