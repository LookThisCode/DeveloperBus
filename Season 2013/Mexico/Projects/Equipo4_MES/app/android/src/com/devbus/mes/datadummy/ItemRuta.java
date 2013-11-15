package com.devbus.mes.datadummy;

public class ItemRuta {
	private int tipo;
	private String ruta;
	private String desc;
	
	public ItemRuta() {}

	public ItemRuta(int tipo, String ruta, String desc) {
		super();
		this.tipo = tipo;
		this.ruta = ruta;
		this.desc = desc;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	

	
	
	
	
	
}
