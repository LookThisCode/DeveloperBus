package br.com.devbus.acesseme;

import javax.servlet.http.HttpServlet;

import br.com.devbus.acesseme.model.Usuario;

import com.google.appengine.api.datastore.Entity;

public class ExisteUsuarioServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5477272534570317279L;

	protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException ,java.io.IOException {
		String email = req.getParameter("email");
		String callback = req.getParameter("callback");
		Entity user = Usuario.getSingleUser(email);
		boolean existe = user != null;
		String json = "{\"existe\":\""+existe+"\"}";
		if(callback == null || callback == ""){
			resp.getWriter().write(json);
		}else{
			resp.getWriter().write(callback+"("+json+")");
		}
	}
}
