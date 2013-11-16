package co.com.agronome.proveedores.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.com.agronome.proveedores.modelo.Proveedor;

import com.google.appengine.api.datastore.Entity;

/**
 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
 * @project agronome
 * @class LoginServlet
 * @date 15/11/2013
 *
 */

public class LoginServlet extends HttpServlet{

	private static final long serialVersionUID = -3352278674092156130L;

	/** 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json; charset=utf-8");
		resp.setHeader("Cache-Control", "no-cache");
		String action = req.getParameter("action");
		PrintWriter out = resp.getWriter();
		if ("sessionUser".equalsIgnoreCase(action)) {
			String name =  (String) req.getSession().getAttribute("user");
			if (name == null) {
				out.println("ERROR");
			}else{
				out.println(Util.writeJSON(Proveedor.getProveedor(name)));
			}
		}
	}
	
	/** 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String usuario = req.getParameter("usuario");
		String pass = req.getParameter("pass");
		
		Entity proveedor = Proveedor.validateProveedor(usuario, pass);
		if (proveedor != null) {
			req.getSession().setAttribute("user", proveedor.getKey().getName());
			PrintWriter out = resp.getWriter();
			out.append("OK");
		}else{
			return;
		}
		
	}
}
