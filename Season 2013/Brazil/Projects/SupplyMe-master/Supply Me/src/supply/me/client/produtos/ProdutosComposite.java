package supply.me.client.produtos;
import supply.me.client.componentes.PainelLateralComposite;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProdutosComposite extends Composite {

    private Panel conteudo = new HorizontalPanel();
    private PainelLateralComposite painelEsquerda = new PainelLateralComposite();
    private Panel painelDireita = new VerticalPanel();

    /**
     * Gestão de produtos.
     */
    public ProdutosComposite(long idCliente) {
      conteudo.add(painelEsquerda);
      inicializarTitulo();
      conteudo.add(painelDireita);

      initWidget(conteudo);
    }
    
    private void inicializarTitulo()
    {
    	Label titulo = new Label();
    	titulo.setText("Produtos");
    	painelDireita.add(titulo);
    }
  }