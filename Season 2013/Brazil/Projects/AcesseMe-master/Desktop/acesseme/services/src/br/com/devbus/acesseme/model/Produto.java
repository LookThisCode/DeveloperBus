package br.com.devbus.acesseme.model;

public class Produto{

	private String titulo;
	private String descricao;
	private String imagem;
	private String link;
	private String preco;
	private String idProduto;
	private String loja;
	private String tipo;
	
	public Produto(String titulo, String descricao, String imagem, String link, String preco, String idProduto, String loja, String tipo){
		this.titulo = titulo;
		this.descricao = descricao;
		this.imagem = imagem;
		this.link = link;
		this.preco = preco;
		this.idProduto = idProduto;
		this.loja = loja;
		this.tipo = tipo;
	}
	
	
	public String getTitulo() {
		return titulo;
	}


	public String getTipo() {
		return tipo;
	}

	public String getLoja() {
		return loja;
	}


	public String getIdProduto() {
		return idProduto;
	}

	public String getDescricao() {
		return descricao;
	}
	public String getImagem() {
		return imagem;
	}
	public String getLink() {
		return link;
	}
	public String getPreco() {
		return preco;
	}
	
	
	
}
