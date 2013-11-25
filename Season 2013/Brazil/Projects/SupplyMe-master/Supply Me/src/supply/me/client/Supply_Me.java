package supply.me.client;

import supply.me.client.produtos.PerfilComposite;
import supply.me.client.produtos.ProdutosComposite;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Supply_Me implements EntryPoint {

	private ProdutosComposite produtos;
	private PerfilComposite perfil;

	public void onModuleLoad() {
		criarComandosCabecalho();
		inicializarApp();
		handleNavigation(History.getToken());
	}

	private void criarComandosCabecalho() {
		// RootPanel.get("cabecalho").
	}

	private void inicializarApp() {
		if (isLogado())
		{
			produtos = new ProdutosComposite(getIdUsuarioLogado());
			perfil = new PerfilComposite();
		}
	}

	private boolean isLogado() {
		// TODO Descobrir como integrar com Google+ Signin para saber se está
		// logado
		return true;
	}
	
	private long getIdUsuarioLogado()
	{
		//TODO Descobrir como pegar o usuário logado
		return -1;
	}

	private void handleNavigation(String token) {
		RootPanel.get("appContainer").clear();
		if ("produtos".equals(token)) {
			RootPanel.get("appContainer").add(produtos);
		} else if ("perfil".equals(token)) {
			RootPanel.get("appContainer").add(perfil);
		} else {
			//RootPanel.get("appContainer").add(new Hyperlink("Produtos", "produtos"));
		}
	}
}
