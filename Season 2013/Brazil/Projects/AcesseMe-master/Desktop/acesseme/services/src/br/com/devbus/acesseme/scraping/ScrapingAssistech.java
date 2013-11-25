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

public class ScrapingAssistech implements Scraping {
	private List<Produto> produtos;
	private String chave;
	private String loja, tipo;
	
	public ScrapingAssistech(String chave, String loja, String tipo) {
		this.chave = chave;
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
		Document doc = Jsoup.connect(
				"http://assistech.com/search/search.php?zoom_sort=0&zoom_query="
						+ chave + "&zoom_per_page=10&zoom_and=1&zoom_cat[]=1")
				.get();
		Elements resultados = doc.getElementsByClass("result_title");
		int cont = 0;
		for (Element e : resultados) {
			Elements links = e.getElementsByTag("a");
			Element l = links.first();
			String linkAux = l.attr("href");

			Document doc2 = Jsoup.connect(linkAux).get();
			Elements artigos = doc2.getElementsByClass("article");
			Elements tabelas = artigos.first().getElementsByAttributeValue(
					"style", "width: 675px");
			if (!tabelas.isEmpty()) {
				List<Element> linhas = tabelas.first().getElementsByTag("tr");
				for (int i = 0; i < linhas.size(); i += 4) {
					if (i + 1 < linhas.size()) {
						Element link2 = linhas.get(i + 1).getElementsByTag("a")
								.first();
						if(link2 == null){
							i++;
							link2 = linhas.get(i + 1).getElementsByTag("a")
									.first();
						}
						String link = "";
						Scanner sc = new Scanner(linkAux);
						sc.useDelimiter("/");
						while (sc.hasNext()) {
							String token = sc.next();
							if (!token.contains(".htm")) {
								link += token + "/";
							}
						}
						sc.close();
						link += link2.attr("href");

						String descricao = linhas.get(i + 1)
								.getElementsByTag("strong").first().text();
						String imagem = linhas.get(i + 1)
								.getElementsByTag("img").first().attr("src");
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
						
						String idProduto = linhas.get(i + 1)
								.getElementsByTag("strong").get(1).text();
						
						Elements precoAux = linhas
								.get(i + 3)
								.getElementsByAttributeValue("style",
										"width: 105px");
						
						if(!precoAux.isEmpty()){
							i++;
						}
						String preco = linhas
								.get(i + 2)
								.getElementsByAttributeValue("style",
										"width: 105px").first().text();
						this.produtos.add(new Produto("", descricao, imagem, link,
								preco, idProduto, loja, tipo));
					}
				}
			}
			cont++;
			if (cont >= 5)
				break;

		}
	}
}
