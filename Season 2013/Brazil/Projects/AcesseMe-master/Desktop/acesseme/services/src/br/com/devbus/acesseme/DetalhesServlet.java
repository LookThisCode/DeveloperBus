package br.com.devbus.acesseme;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.devbus.acesseme.model.Produto;
import br.com.devbus.acesseme.scraping.Scraping;
import br.com.devbus.acesseme.scraping.ScrapingProdutoAssistech;

public class DetalhesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8452062303158102206L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String site = req.getParameter("site");
		String callback = req.getParameter("callback");
		Scraping s = new ScrapingProdutoAssistech(site);
		s.execute();
		Produto p = s.getProdutos().get(0);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		if(callback == null || callback == ""){
			resp.getWriter().write(gson.toJson(p));
		}else{
			resp.getWriter().write(callback+"("+gson.toJson(p)+")");
		}
	}
}
