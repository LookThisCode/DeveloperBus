package com.want_it.clases;

import android.util.Pair;

public class Oferta {
	String id;
	String ancho;
	String categoria;
	String ciudad;
	String colores;
	String idUsuario;
	String largo;
	String materiales;
	String presupuesto;
	
	public Oferta(String id){
		this.id=id;
	}
	public void setAttribute(Pair<String, String>data){
		if(data.first.equals("Ancho")){
			ancho=data.second;
			return;
		}
		if(data.first.equals("Categoria")){
			categoria=data.second;
			return;
		}
		if(data.first.equals("Ciudad")){
			ciudad=data.second;
			return;
		}
		if(data.first.equals("Colores")){
			colores=data.second;
			return;
		}
		if(data.first.equals("IdUsuario")){
			idUsuario=data.second;
			return;
		}
		if(data.first.equals("Largo")){
			largo=data.second;
			return;
		}
		if(data.first.equals("Materiales")){
			materiales=data.second;
			return;
		}
		if(data.first.equals("Presupuesto")){
			presupuesto=data.second;
			return;
		}
	}
}
