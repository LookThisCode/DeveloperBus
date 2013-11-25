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

public class ScrapingProdutoAssistech implements Scraping {

	private String site;
	private List<Produto> produtos;
	
	public ScrapingProdutoAssistech(String site){
		this.site = site;
		this.produtos = new ArrayList<Produto>();
	}
	@Override
	public List<Produto> getProdutos() {
		// TODO Auto-generated method stub
		return produtos;
	}

	@Override
	public String getChave() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute() throws IOException {
		Document doc = Jsoup.connect(site).get();
		Element article = doc.getElementsByClass("article").first();
		String titulo = article.getElementsByTag("h1").first().getElementsByTag("span").first().text();
		Elements paragrafos = article.getElementsByTag("p");
		String descricao = "";
		if(paragrafos.size() >= 2){
			descricao = paragrafos.first().toString() + paragrafos.get(1).toString();
		}else{
			descricao = paragrafos.first().toString();
		}
		String preco = article.getElementsByClass("txtbold").first().text();
		Scanner sc = new Scanner(preco);
		if(preco.contains("$")){
			sc.useDelimiter("[$\\*]");
			System.out.println(sc.next());
			preco = "$"+sc.next();
		}else{
			sc.useDelimiter("[\\*]");
			preco = sc.next();
			Scanner sc2 = new Scanner(preco);
			while(sc2.hasNext()){
				preco = sc2.next();
			}
			preco = "$"+preco;
			sc2.close();
		}
		sc.close();
		sc = new Scanner(site);
		sc.useDelimiter("/");
		String idProduto = "";
		while(sc.hasNext()){
			String aux = sc.next();
			if(aux.contains(".htm")){
				idProduto = aux.replace(".htm", "").trim();
			}
		}
		sc.close();
		//String link = "http://www.assistech.com/cgi-bin/shopper.cgi?add=action&amp;key="+idProduto.toUpperCase();
		//f="http://www.assistech.com/cgi-bin/shopper.cgi?add=action&amp;key=TVEARSDIGITAL
		Elements linkAux = article.getElementsByAttributeValue("style","width:66%;text-align:center");
		if(linkAux.isEmpty()){
			linkAux = article.getElementsByAttributeValue("style","width:50%;text-align:center");
		}
		String link = linkAux.first().getElementsByTag("a").attr("href");
		String imagem = article.getElementsByAttributeValue("class", "out image").first().getElementsByTag("img").first().attr("src");
		sc = new Scanner(imagem);
		sc.useDelimiter("/");
		while (sc.hasNext()) {
			String token = sc.next();
			if (token.contains(".jpg") || token.contains(".gif")) {
				imagem = token;
			}
		}
		sc.close();
		imagem = "http://assistech.com/images/" + imagem;
		produtos.add(new Produto(titulo, descricao, imagem, link, preco, idProduto, "Assistech", "2"));
	}

}
