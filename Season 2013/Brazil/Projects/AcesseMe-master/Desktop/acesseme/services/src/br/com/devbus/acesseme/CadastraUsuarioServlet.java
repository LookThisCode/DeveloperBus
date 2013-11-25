package br.com.devbus.acesseme;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.devbus.acesseme.model.Usuario;

public class CadastraUsuarioServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3121853543132085088L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String email = req.getParameter("email");
		String necessidade = req.getParameter("n");
		String token = req.getParameter("token");
		Usuario.createUser(email, necessidade, token);
	}
}
