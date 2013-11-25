package com.alow.service.crud.util;

import java.util.ArrayList;

public class CrudUtil<ENT> {
	private ArrayList<ENT> lista;
	private String mensagem;
	
	public ArrayList<ENT> getLista() {
		return lista;
	}
	public void setLista(ArrayList<ENT> lista) {
		this.lista = lista;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
