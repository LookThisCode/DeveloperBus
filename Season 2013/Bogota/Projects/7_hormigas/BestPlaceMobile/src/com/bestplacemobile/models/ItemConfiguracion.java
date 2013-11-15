package com.bestplacemobile.models;

public class ItemConfiguracion {

	private String info;
	private String numNotificaciones;
	private int tipo;
	
	public ItemConfiguracion(String info, String numNotificaciones, int tipo){
		this.info = info;
		this.numNotificaciones = numNotificaciones;
		this.tipo = tipo;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getNumNotificaciones() {
		return numNotificaciones;
	}

	public void setNumNotificaciones(String numNotificaciones) {
		this.numNotificaciones = numNotificaciones;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
		
}
