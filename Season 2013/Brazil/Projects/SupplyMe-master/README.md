SupplyMe
========

APIs Google utilizadas

- Sign In do Google Plus Javascript: É possível logar e deslogar
  Não está sendo armazenado os dados do usuário logado ainda. A intenção é gravar as informações de login quando a tela de cadastro do perfil for criada.

- Maps API Javascript: O mapa é mostrado e markers são criados para mostrar a posição dos fornecedores. Depois será necessário acrescentar a interação com os markers (ao clicar em um marker, destacar o elemento correspondente e vice-versa)

- App Engine Java: O App engine foi usado para armazenar os dados e webservices disponibilizam estes dados para consumo pelo front-end. Quando as demais telas forem feitas os webservices serão RESTful para permitir incluir, excluir, alterar e obter os elementos.

- Search API App Engine: Descobri esta API ontem. Ela permite fazer busca textual geolocalizada. Ela está sendo utilizada para permitir buscar por produtos que atendam às palavras chave e que estejam em uma região geográfica determinada (no caso é usada a região ao redor do centro do mapa sendo mostrado)

- GWT: Como não houve tempo para construir a área de administração, ele será utilizado para fazer as telas de cadastro de produtos, perfil do usuário, etc. Será integrado com o back-end App Engine.
