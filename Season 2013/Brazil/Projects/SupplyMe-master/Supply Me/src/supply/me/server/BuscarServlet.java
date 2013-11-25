package supply.me.server;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchException;
import com.google.appengine.api.search.SearchServiceFactory;

/**
 * Classe que faz a busca utilizando a localização e os termos usados no campo de busca.
 * 
 * Foi utilizada A Search API do App Engine para permitir a busca 
 * geolocalizada combinada com a busca textual
 * @author carvalhorr
 *
 */
@SuppressWarnings("serial")
public class BuscarServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/json");
		// writeFornecedoresJson(resp, getFornecedoresFake());
		procurar(req, resp);
	}

	private void procurar(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			String search = req.getParameter("q");
			String lat = req.getParameter("lat");
			String lng = req.getParameter("lng");
			String distancia = req.getParameter("distancia");
			IndexSpec indexSpec = IndexSpec.newBuilder().setName("produtos")
					.build();
			Index index = SearchServiceFactory.getSearchService().getIndex(
					indexSpec);
			String query_string = "";
			if (!"".equals(search)) query_string = search + " AND ";
			query_string += "distance(posicao, geopoint(" + lat + ", " + lng
					+ ")) < " + distancia;
			Results<ScoredDocument> results = index.search(query_string);

			boolean first = true;
			resp.getWriter().write("[");
			for (Iterator iterator = results.iterator(); iterator.hasNext();) {
				ScoredDocument doc = (ScoredDocument) iterator.next();
				if (!first) resp.getWriter().write(", ");
				first = false;
				boolean first_field = true;
				resp.getWriter().write("{");
				for (Iterator fields = doc.getFields().iterator(); fields
						.hasNext();) {
					if (!first_field) resp.getWriter().write(", ");
					first_field = false;
					Field field = (Field) fields.next();
					if ("posicao".equals(field.getName())) {
						resp.getWriter().write("\"latitude\": ");
						resp.getWriter().write("\"" + field.getGeoPoint().getLatitude() + "\", ");
						resp.getWriter().write("\"longitude\": ");
						resp.getWriter().write("\"" + field.getGeoPoint().getLongitude() + "\"");
					} else {
						resp.getWriter().write("\"" + field.getName() + "\": ");
						resp.getWriter().write("\"" + field.getText() + "\"");
					}
				}
				resp.getWriter().write("}");
			}
			resp.getWriter().write("]");
		} catch (SearchException e) {
			// handle exception...
		}
	}
}
