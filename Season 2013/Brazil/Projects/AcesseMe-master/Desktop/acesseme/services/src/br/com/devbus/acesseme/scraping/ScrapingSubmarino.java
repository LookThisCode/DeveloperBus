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


public class ScrapingSubmarino implements Scraping{
	private List<Produto> produtos;
	private String chave;
	private String page;
	private final String SITE = "http://busca.submarino.com.br/busca.php?q=";
	private final String PAGINA = "&page=";
	private String loja, tipo;
	
	public ScrapingSubmarino(String chave, String page, String loja, String tipo){
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
		Document doc = Jsoup.connect(SITE+chave+PAGINA+page).get();
		Elements produtos = doc.getElementsByClass("hproduct");
		for(Element e: produtos){
			Elements imagens = e.getElementsByClass("photo");
			Element img = imagens.first();
			String imagem = img.attr("src");
			
			Elements content = e.getElementsByClass("neemu-price-container");
			Elements links = content.first().getElementsByTag("a");
			Element l = links.first();
			String link = l.attr("href");
			Scanner sc = new Scanner(link);
			sc.useDelimiter("link=");
			sc.next();
			link = sc.next().trim();
			sc.close();
			sc = new Scanner(link);
			sc.useDelimiter("idproduto=");
			sc.next();
			String idProduto = sc.next().trim();
			sc.close();
			String descricao = l.attr("title");
		//	Document doc2 = Jsoup.connect(link).get();
		//	Elements precos = doc2.getElementsByAttributeValue("itemprop", "price");
		//	Elements precos = content.first().getElementsByTag("strong");
		//	String preco = precos.first().text();
			
			//this.produtos.add(new Produto(descricao, imagem, link, preco));
			this.produtos.add(new Produto("", descricao, imagem, link, "99,99", idProduto, loja, tipo));
		}
		
	}
	
	
}
