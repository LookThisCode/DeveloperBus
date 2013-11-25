package br.com.devbus.acesseme.scraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.devbus.acesseme.model.Produto;

public class ScrapingMagazineLuiza implements Scraping {

	private List<Produto> produtos;
	private String chave;
	private String page;
	private final String SITE = "http://www.magazineluiza.com.br";
	private String loja, tipo;
	
	public ScrapingMagazineLuiza(String chave, String page, String loja, String tipo){
		this.chave = chave;
		this.page = page;
		this.produtos = new ArrayList<Produto>();
		this.loja = loja;
		this.tipo = tipo;
	}
	@Override
	public List<Produto> getProdutos() {
		// TODO Auto-generated method stub
		return produtos;
	}

	@Override
	public String getChave() {
		// TODO Auto-generated method stub
		return chave;
	}

	@Override
	public void execute() throws IOException {
		Document doc = Jsoup.connect(SITE+"/busca/"+chave+"/"+page).get();
		Elements produtos = doc.getElementsByClass("product");
		for(Element e: produtos){
			Elements imagens = e.getElementsByClass("product-image");
			Element img = imagens.first();
			String imagem = img.attr("src");
			
			Elements links = e.getElementsByTag("a");
			Element l = links.first();
			String link = SITE+l.attr("href");
			
			Elements descricoes = e.getElementsByClass("productTitle");
			String descricao = descricoes.first().text().trim();
			
			Elements ids = e.getElementsByAttributeValue("itemprop", "productID");
			String idProduto = ids.first().attr("content");
			
			Elements precos = e.getElementsByAttributeValue("itemprop", "price");
			String preco = precos.first().text().replace("por", "").trim();
			preco = preco.replace("R$", "").trim();
			
			this.produtos.add(new Produto("", descricao, imagem, link, preco, idProduto, loja, tipo));
		}
	}

}
