VillaDoCorretor
===============
.
Villa Do Corretor


A Villa do Corretor se coloca em um mercado cada vez em expansão no Brasil, o mercado imobiliário, propondo dar mais facilidades ao corretor autônomo que muitas vezes depende da imobiliária para gerir suas carterias de clientes, muitas vezes devido a custos operacionais.


Para começar a utilizara a aplicação comece criando uma gonta no google plus, acessando http://accounts.google.com/

Modifique sua root foolder do servidor web php para a pasta public do projeto, ela quem gerenciará includes e dependências do projeto.



REST É VIDA!

Para facilitar a interação cross plataforma todo o sistema está utilizando REST, tanto para salvar quanto para resgatar informações.

A principal estrutura de REST é http://<sua url>/json?

choice = "nome das funcoes";

outros parametros = 'id' 'me' 'user';

exemplo: http://trekoapp.appspot.com/json?choice=user&id=106003374795503086342

{
id: "3",
name: "Thiago Masano Cavaloti",
plusid: "106003374795503086342",
plusurl: "https://plus.google.com/106003374795503086342",
email: "thiagocavaloti@gmail.com",
device: "",
confirmed: "",
imagem: "https://lh4.googleusercontent.com/-R8FNYkIiKIw/AAAAAAAAAAI/AAAAAAAAAjQ/mx4AsLDrmvM/photo.jpg?sz=50",
}

retornara um json:

next sprint:

integracao ao calendar;
integracao ao google drive; // pq nao usar google store? $$$ como a quantidade de imagens nao sera enorme podemos explorar os 15GB doados pela Google =P

obs.: para consumir todas as funcionalidades crie uma conta cloud.google.com

Make with love and coffe.

