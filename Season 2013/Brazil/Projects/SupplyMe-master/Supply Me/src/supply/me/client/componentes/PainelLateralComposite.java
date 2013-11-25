package supply.me.client.componentes;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PainelLateralComposite extends Composite{
	
	private Panel painel = new VerticalPanel();
	private Hyperlink linkPerfil = new Hyperlink("Perfil", "perfil");
	private Hyperlink linkVendas = new Hyperlink("Vendas", "vendas");
	private Hyperlink linkCompras = new Hyperlink("Compras", "compras");
	private Hyperlink linkFornecedores = new Hyperlink("Fornecedores", "fornecedores");
	private Hyperlink linkProdutos = new Hyperlink("Produtos", "produtos");
	
	public PainelLateralComposite()
	{
		montarComponente();
	}
	
	private void montarComponente()
	{
		painel.add(linkPerfil);
		painel.add(linkVendas);
		painel.add(linkCompras);
		
		initWidget(painel);
	}
}
