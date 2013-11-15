package co.com.agronome.proveedores.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.com.agronome.proveedores.modelo.Proveedor;

import com.google.appengine.api.datastore.Entity;

/**
 * Servlet de Proveedores 
 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
 * @project agronome
 * @class ProveedorServlet
 * @date 15/11/2013
 *
 */
public class ProveedorServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6706625513171123757L;
	private static final Logger logger = Logger.getLogger(ProveedorServlet.class.getCanonicalName());
	
	/**
	 * Get the requested customer entities in JSON format
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		super.doGet(req, resp);
		logger.log(Level.INFO, "Obtaining Customer listing");
		String searchFor = req.getParameter("q");
		PrintWriter out = resp.getWriter();
		Iterable<Entity> entities = null;

		if (searchFor == null || searchFor.equals("")) {
			entities = Proveedor.getAllProveedores();
			out.println(Util.writeJSON(entities));
		} else {
			entities = Proveedor.getProveedor(searchFor);
			out.println(Util.writeJSON(entities));
		}
		return;
	}

	/**
	 * Insert the new customer
	 */
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.log(Level.INFO, "Creating Proveedor");
		String usuario = req.getParameter("usuario");
		String pass = req.getParameter("pass");
		String nit = req.getParameter("nit");
		String nombre = req.getParameter("nombre");
		String etiquetas = req.getParameter("etiquetas");
		String telefono = req.getParameter("telefono");
		Proveedor.createOrUpdateProveedor(usuario, pass, nit, nombre, etiquetas, telefono);
		resp.sendRedirect("index.html");
	}

	/**
	 * Delete the customer
	 */
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	/**
	 * Redirect the call to doDelete or doPut method
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		if (action.equalsIgnoreCase("delete")) {
			doDelete(req, resp);
			return;
		} else if (action.equalsIgnoreCase("put")) {
			doPut(req, resp);
			return;
		}
	}
}
