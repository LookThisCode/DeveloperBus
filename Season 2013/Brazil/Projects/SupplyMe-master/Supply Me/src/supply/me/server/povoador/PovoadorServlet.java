package supply.me.server.povoador;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GeoPoint;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;

import supply.me.server.datastore.Fornecedor;

/**
 * Classe que insere produtos para fazer a busca funcionar.
 * Esta classe somente será usada uma vez a cada novo deploy do app
 * @author carvalhorr
 *
 */
@SuppressWarnings("serial")
public class PovoadorServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/json");
		povoar();
		resp.getWriter().write("[\"status\": \"OK\"]");
	}

	private void povoar() {
		criarFornecedor1();
		criarFornecedor2();
		criarFornecedor3();
	}

	private void criarFornecedor1() {
		Fornecedor f = addFornecedor(1, "Papelaria", "Papelaria da esquina.",
				-23.60135, -46.68607);
		addProduto(f, "Bloco de papel com 10 folhas",
				"Bloco de papel com 10 folhas Papelzinho.");
		addProduto(f, "Borracha retangular",
				"Borracha retangular de 20x50mm Borrachuda.");
	}

	private void criarFornecedor2() {
		Fornecedor f = addFornecedor(
				2,
				"Material de limpeza Inc.",
				"Empresa especializada em fornecimento de materiais de limpeza para escritorio.",
				-23.59941, -46.68178);
		addProduto(f, "Papel higienico dupla face Carinho 100 mts",
				"Papel higienico dupla face marca Carinho 10 rolos de 100 metros.");
		addProduto(f, "Papel higienico dupla face Carinho 50 mts",
				"Papel higienico dupla face marca Carinho 10 rolos de 50 metros.");
		addProduto(f, "Sabão em pó 10kg Homo",
				"Pacote de 10kg de sabao em po da marca Homo.");
		addProduto(f, "Sabão em pó 5kg Homo",
				"Pacote de 5kg de sabao em po da marca Homo.");
		addProduto(f, "Detergente liquido para chao Limpura 4l",
				"Detergente liquido Limpura pra limpeza de chao com 4l.");
		addProduto(f, "Detergente liquido para banheiro Limpura 2l",
				"Detergente liquido Limpura pra limpeza de banheiro com 2l.");
	}

	private void criarFornecedor3() {
		Fornecedor f = addFornecedor(3, "Material para escritorio",
				"Fornecedora de suprimentos para escritorio em geral.",
				-23.59962, -46.68786);
		addProduto(f, "Papel A3/50", "Pacote de papel A3 com 50 folhas.");
		addProduto(f, "Papel A3/100", "Pacote de papel A3 com 100 folhas.");
		addProduto(f, "Papel A4/50", "Pacote de papel A4 com 50 folhas.");
		addProduto(f, "Papel A4/100", "Pacote de papel A4 com 100 folhas.");
		addProduto(f, "Grampo tamanho 1 com 100",
				"Grampo de cobre para grampeador com 100 tamanho 1.");
		addProduto(f, "Grampo tamanho 2 com 100",
				"Grampo de cobre para grampeador com 100 tamanho 2.");
	}

	private Fornecedor addFornecedor(long id, String nome, String desc,
			double lat, double lng) {
		Fornecedor f = new Fornecedor();
		f.setId(id);
		f.setNome(nome);
		f.setDescricao(desc);
		f.setLatitude(lat);
		f.setLongitude(lng);
		return f;
	}

	private void addProduto(Fornecedor fornecedor, String nome, String descricao) {
		Document doc = Document
				.newBuilder()
				.addField(Field.newBuilder().setName("nome").setText(nome))
				.addField(
						Field.newBuilder().setName("descricao")
								.setText(descricao))
				.addField(
						Field.newBuilder().setName("nome_fornecedor")
								.setText(fornecedor.getNome()))
				.addField(
						Field.newBuilder().setName("descricao_fornecedor")
								.setText(fornecedor.getDescricao()))
				.addField(
						Field.newBuilder()
								.setName("posicao")
								.setGeoPoint(
										new GeoPoint(fornecedor.getLatitude(),
												fornecedor.getLongitude())))
				.build();
		indexADocument("produtos", doc);
	}

	public void indexADocument(String indexName, Document document) {
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
		Index index = SearchServiceFactory.getSearchService().getIndex(
				indexSpec);

		try {
			index.put(document);
		} catch (PutException e) {
			if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult()
					.getCode())) {
				// retry putting the document
			}
		}
	}
}
