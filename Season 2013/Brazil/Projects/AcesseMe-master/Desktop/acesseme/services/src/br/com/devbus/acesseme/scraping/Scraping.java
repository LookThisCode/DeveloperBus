package br.com.devbus.acesseme.scraping;

import java.io.IOException;
import java.util.List;

import br.com.devbus.acesseme.model.Produto;

public interface Scraping {

	public List<Produto> getProdutos();
	public String getChave();
	public void execute() throws IOException;
}
