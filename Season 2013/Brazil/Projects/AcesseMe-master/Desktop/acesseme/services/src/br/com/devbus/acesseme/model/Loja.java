package br.com.devbus.acesseme.model;

import java.io.IOException;
import java.util.List;

import br.com.devbus.acesseme.scraping.Scraping;

public class Loja{
	private int tipo;
	private List<Produto> produtos;
	private String nome;
	private Scraping scraping;
	
	public Loja(String nome, Scraping scraping, int tipo){
		this.nome = nome;
		this.scraping = scraping;
		this.tipo = tipo;
	}
		
	public int getTipo() {
		return tipo;
	}

	public String getNome() {
		return nome;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void run() throws IOException {
		scraping.execute();
		produtos = scraping.getProdutos();
	}

}
