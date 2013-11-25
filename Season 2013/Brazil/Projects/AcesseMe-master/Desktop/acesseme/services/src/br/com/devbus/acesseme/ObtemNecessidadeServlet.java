package br.com.devbus.acesseme;

import javax.servlet.http.HttpServlet;

import br.com.devbus.acesseme.model.Usuario;

import com.google.appengine.api.datastore.Entity;

public class ObtemNecessidadeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2321121557816689239L;

	protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException ,java.io.IOException {
		String email = req.getParameter("email");
		String token = req.getParameter("token");
		String callback = req.getParameter("callback");
		Entity user = Usuario.getSingleUser(email);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		if(user.getProperty("token").equals(token)){
			String necessidade = user.getProperty("necessidade")+"";
			String json = "{\"necessidade\":\""+necessidade+"\"}";
			if(callback == null || callback == ""){
				resp.getWriter().write(json);
			}else{
				resp.getWriter().write(callback+"("+json+")");
			}
		}
	}
}
