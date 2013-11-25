package br.com.devbus.acesseme.scraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.devbus.acesseme.model.Produto;

public class ScrapingWallMart implements Scraping {

	private List<Produto> produtos;
	private String chave;
	private String page;
	private final String SITE = "http://www.walmart.com.br";
	private final String BUSCA = "/busca/?ft=";
	private final String PAGINA = "&PageNumber=";
	private String loja, tipo;
	
	public ScrapingWallMart(String chave, String page, String loja, String tipo){
		this.chave = chave;
		this.produtos = new ArrayList<Produto>();
		this.page = page;
		this.loja = loja;
		this.tipo = tipo;
	}


	public List<Produto> getProdutos() {
		return produtos;
	}

	public String getChave() {
		return chave;
	}

	@Override
	public void execute() throws IOException {
		Document doc = Jsoup.connect(SITE+BUSCA+chave+PAGINA+page).get();
		Elements produtos = doc.getElementsByClass("shelf-item");
		for(Element e: produtos){
			Elements imagens = e.getElementsByClass("lazy-image");
			Element img = imagens.first();
			String imagem = img.attr("data-src");
			
			Elements links = e.getElementsByTag("a");
			Element l = links.first();
			String link = (SITE+l.attr("href")).trim();
			
			Scanner sc = new Scanner(link);
			sc.useDelimiter("/");
			String aux = sc.next(); 
			while(sc.hasNext()) aux = sc.next();
			aux = aux.trim();
			sc.close();
			sc = new Scanner(aux);
			sc.useDelimiter("-");
			String idProduto = sc.next().trim();
			sc.close();
			
			Elements descricoes = e.getElementsByClass("product-title");
			String descricao = descricoes.first().text();
			
			Elements precos1 = e.getElementsByClass("int");
			Elements precos2 = e.getElementsByClass("dec");
			String preco = precos1.first().text()+precos2.first().text();

			this.produtos.add(new Produto("", descricao, imagem, link, preco, idProduto, loja, tipo));
		}
		
	}
	


}
