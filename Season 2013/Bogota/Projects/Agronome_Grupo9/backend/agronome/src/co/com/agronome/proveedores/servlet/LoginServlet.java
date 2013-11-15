package co.com.agronome.proveedores.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;

import co.com.agronome.proveedores.modelo.Proveedor;

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
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	
	/** 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String usuario = req.getParameter("usuario");
		String pass = req.getParameter("pass");
		PrintWriter out = resp.getWriter();
		
		Entity proveedor = Proveedor.getSingleProveedor(usuario, pass);
		if (proveedor != null) {
			List<Entity> entities = new ArrayList<Entity>();
			entities.add(proveedor);
			out.println(Util.writeJSON( entities));
			req.getSession().setAttribute("user", proveedor);
		}else{
			return;
		}
		
		super.doPost(req, resp);
	}
}
