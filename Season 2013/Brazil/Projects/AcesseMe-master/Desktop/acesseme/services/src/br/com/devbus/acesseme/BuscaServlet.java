package br.com.devbus.acesseme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.devbus.acesseme.model.Loja;
import br.com.devbus.acesseme.scraping.ScrapingAssistech;
import br.com.devbus.acesseme.scraping.ScrapingMagazineLuiza;
import br.com.devbus.acesseme.scraping.ScrapingWallMart;

import com.google.gson.Gson;


public class BuscaServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3075830666541072193L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String chave = req.getParameter("chave");
		String page = req.getParameter("page");
		String callback = req.getParameter("callback");
		Loja magazineLuiza = new Loja("Magazine Luiza", new ScrapingMagazineLuiza(chave, page, "Magazine Luiza", "3"), 3);
		magazineLuiza.run();
		Loja wallmart = new Loja("Wallmart", new ScrapingWallMart(chave, page, "Wallmart", "3"), 3);
		wallmart.run();
		Loja assistech = new Loja("Assistech", new ScrapingAssistech(chave, "Assistech", "2"), 2);
		assistech.run();
		List<Loja> lojas = new ArrayList<Loja>();
		lojas.add(assistech);
		lojas.add(magazineLuiza);
		lojas.add(wallmart);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		if(callback == null || callback == ""){
			resp.getWriter().write(gson.toJson(lojas));
		}else{
			resp.getWriter().write(callback+"("+gson.toJson(lojas)+")");
		}
	}
	
	
}
