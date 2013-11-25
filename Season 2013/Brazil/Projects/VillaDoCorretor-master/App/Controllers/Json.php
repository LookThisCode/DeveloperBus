<?php
/**
 * Created by JetBrains PhpStorm.
 * User: guilherme
 * Date: 19/08/13
 * Time: 13:48
 * To change this template use File | Settings | File Templates.
 */

namespace App\Controllers;
use VILLA\Controller\Action,
    APP\Models,
    VILLA\Di\Container;


class Json extends Action {


    public function index()
    {
    	//echo $this->imo("123");
    	//echo $this->getImovel();

    	if(isset($_GET['choice']))
			$bingo = $_GET['choice'];
		else
			$bingo = null;

			switch ($bingo) {
				
                // seleciona usuario por id
			case 'user':
			echo $this->user($_GET['id']);
			break;
                // seleciona imovel por id
			case 'imo':
			echo $this->getImovel($_GET['id']);
			break;
			case 'me':
                // seleciona todos os imoveis de um determinado corretor
			echo $this->fetchImovel($_GET['id']);
			break;
                // salvar usuario
			case 'saveme':
			echo $this->salvarUsuario($_GET['me']);
			break;
                // seleciona todas as fotos de um determinado imovel
			case 'pics':
			echo $this->fetchFotos($_GET['id']);
			break;
                //  seleciona profile de um determinado usuario pelo plusid
			case 'profile':
			echo $this->getProfile($_GET['id']);
			break;
                // seleciona favoritos de um determinado usuario
			case 'fav':
			echo $this->getMyFav($_GET['id']);
			break;
                // seleciona as interações de um determinado corretor com todos os usuarios
			case 'int':
			echo $this->getMyInts($_GET['id']);
			break;
                // salva perfil do cliente ou corretor, distinto pelo tipo
            case 'saveprofile':
            echo $this->saveProfile($_GET['user']);
            break;
                // salva imovel
            case 'saveimo':
            echo $this->saveImovel($imo);
            break;
                // salva iteracao com o cliente
            case 'saveit':
            echo $this->saveIteracao();
            break;
                // seleciona interacao com um determinado cliente
			case 'intu':
			echo $this->getIntsUser($_GET['id']);
			break;
                // salva relacionamento dos clientes com os corretores para montar lista de interações
            case 'saveRel':
            echo $this->saveRelacionamento();
            break;
                // salvar favoritos de um cliente
            case 'saveFav':
            echo $this->saveFavorito();
            break;
                // salvar longitude e latitude
            case 'longlat':
            echo $this->getLongLat($_GET['endereco']);
            break;
			default:
			echo "[nothing to you here]";
			break;
		}

        $this->render("index");
    }

   	public function conectaBanco(){


            $bd='treko';
            $conexao=mysql_connect(":/cloudsql/gcdc2013-partyme:treko", "treko-db", "minguado") or die (mysql_error());
            mysql_set_charset('utf8',$conexao);
            mysql_select_db($bd,$conexao) or die (mysql_error());

    } 

    public function user($id){

        $this->conectaBanco();
        $result = mysql_query("SELECT * FROM user where plusid = $id ");

        $user = mysql_fetch_array($result);

        return json_encode($user);
                
    }

    public function salvarUsuario($user){

		$name = $user['nome'];
		$plusid = $user['plusid'];
		$plusurl = $user['plusurl'];
		$email = $user['email'];
		$device = $user['device'];
		$confirmed = $user['confirmado'];
        $image  = $user['imagem'];
        $token = $user['token'];

		$this->conectaBanco();

		return	$query = mysql_query("INSERT INTO user (name, plusid, plusurl, email, device, confirmed, imagem, token) values ('$name', '$plusid', '$plusurl', '$email', '$device', '$confirmed', '$image', '$token')") or die(mysql_error());

    }

    public function getImovel($id){

    	$this->conectaBanco();
        $result = mysql_query("SELECT * FROM imovel where id = $id");

        $imovel = mysql_fetch_array($result);

        return json_encode($imovel);

    }

    public function fetchImovel($id){

    	$this->conectaBanco();
    	$result = mysql_query("SELECT * FROM imovel where corretor_id = $id");

    	$imo['imovel'] = array();

    	$f = $this->fetchFotos($imo);

		while($row = mysql_fetch_array($result)){
			$foto = json_decode($this->fetchFotos($row['id']));
			$a = array("id" => $row['id'], "banho" => $row['banho'], "suites" => $row['suites'], "quartos" => $row['quartos'], "vagas" => $row['iptu'], "condominio" => $row['condominio'], "tipo" => $row['tipo'], "bairro" => $row['bairro'], "descricao" => $row['descricao'], "preco" => $row['preco'], "municipio" => $row['municipio'], "categoria" => $row['categoria'], "area_util" => $row['area_util'], "area_total" => $row['area_total'], "salas" => $row['salas'], "descricao_vagas" => $row['descricao_vagas'], "pavimentos" => $row['pavimentos'], "por_andar" => $row['por_andar'], "sol_da_manha" => $row['sol_da_manha'], "interfone" => $row['interfone'], "sauna" => $row['sauna'], "portaria_permanente" => $row['portaria_permanente'], "corretor_id" => $row['corretor_id'], "foto" =>$foto, "location" => ["lat" => $row['lat'], "long" => $row['long'] ]);			
			array_push($imo['imovel'], $a);
		}

		return json_encode($imo);

    }

    public function fetchFotos($imo){

    	$this->conectaBanco();
    	$result = mysql_query("SELECT * FROM foto where id_imovel = $imo");

    	$f = array();


		while($row = mysql_fetch_array($result)){
			$a = array("id" => $row['id_foto'], "url_foto" => $row['url_foto'], "desc_foto" => $row['descricao_foto'], "id_imovel" => $row['id_imovel']);
			array_push($f, $a);
		}

		return json_encode($f);

    }

    public function getProfile($id){

    	$this->conectaBanco();
        $result = mysql_query("SELECT * FROM profile where plusid = $id");

        $profile = mysql_fetch_array($result);

        return json_encode($profile);

    }

    public function getBairro($bairro){

    	$this->conectaBanco();
        $result = mysql_query("SELECT * FROM bairro where bairro = $bairro");

        $bairro = mysql_fetch_array($result);

        return json_encode($bairro);

    }

    public function getMyFav($iduser){

    	$this->conectaBanco();
        $result = mysql_query("SELECT * FROM favoritos where id_usuario = $iduser");

       $f = array();

		while($row = mysql_fetch_array($result)){
			$a = array("id_imovel" => $row['id_imovel'], "id_usuario" => $row['id_usuario'], "id_corretor" => $row['id_corretor'], "date" => $row['date'], "status" => $row['status']);
			array_push($f, $a);
		}

		return json_encode($f);

    }

    public function getMyInts($idcor){

    	$this->conectaBanco();
        $result = mysql_query("SELECT * FROM interacao where id_corretor = $idcor");

       $f = array();

		while($row = mysql_fetch_array($result)){
			$a = array("id_corretor" => $row['id_corretor'], "id_usuario" => $row['id_usuario'], "message" => $row['message'], "metodo" => $row['metodo'], "data" => $row['data'], "iditeracao" => $row['iditeracao']);
			array_push($f, $a);
		}

		return json_encode($f);

    }

    public function getIntsUser($iduser){

    	$this->conectaBanco();
        $result = mysql_query("SELECT * FROM interacao where id_usuario = $iduser");

       $f = array();

		while($row = mysql_fetch_array($result)){
			$a = array("id_corretor" => $row['id_corretor'], "id_usuario" => $row['id_usuario'], "message" => $row['message'], "metodo" => $row['metodo'], "data" => $row['data'], "iditeracao" => $row['iditeracao']);
			array_push($f, $a);
		}

		return json_encode($f);

    }

    protected function saveImovel($imo){

        $this->conectaBanco();

    	$banho = $imo['banhis'];
        $suites = $imo['suites'];
        $quartos = $imo['qrt']; 
        $vagas = $imo['vagas']; 
        $iptu = $imo['iptu']; 
        $condominio = $imo['condominio']; 
        $tipo = $imo['tipo']; 
        $bairro = $imo['bairro']; 
        $descricao = $imo['desc']; 
        $preco = $imo['preco']; 
        $municipio = $imo['municipio']; 
        $categoria = $imo['categoria']; 
        $area_util = $imo['area_util']; 
        $area_total = $imo['area_total']; 
        $salas = $imo['salas']; 
        $descricao_vagas =  $imo['desc_vagas']; 
        $pavimentos = $imo['pavimentos']; 
        $por_andar = $imo['porandar']; 
        $sol_da_manha = $imo['sol_manha']; 
        $interfone = $imo['interfone']; 
        $sauna = $imo['sauna']; 
        $portaria_permanente =  $imo['p_permanente']; 
        $elevador = $imo['elevador']; 
        $porteiro_fisico = $imo['p_fisico']; 
        $idade =  $imo['idade']; 
        $armario_cozinha = $imo['a_cozinha'];  
        $armario_quartos = $imo['a_quartos']; 
        $dce = $imo['dce']; 
        $piscina =  $imo['piscina']; 
        $salao_festas =  $imo['s_festas']; 
        $hall_decorado =  $imo['hall_dec']; 
        $circuito_tv =  $imo['circuito_tv']; 
        $area_verde = $imo['area_verde']; 
        $epaco_gourmet = $imo['esp_gourmet']; 
        $corretor_id = $imo['id_corretor']; 
        $espaco_gourmet = $imo['egourmet'];

    	return	$query = mysql_query("INSERT INTO imovel (banho, suites, quartos, vagas, iptu, condominio, tipo, bairro, descricao, preco, municipio, categoria, area_util, area_total, salas, descricao_vagas, pavimentos, por_andar, sol_da_manha, interfone, sauna, portaria_permanente, elevador, porteiro_fisico, idade, armario_cozinha, armario_quartos, dce, piscina, salao_festas, hall_decorado, circuito_tv, area_verde, espaco_gourmet, corretor_id) values ('$banho', '$suites', '$quartos', '$vagas', '$iptu', '$condominio', '$tipo', '$bairro', '$descricao', '$preco', '$municipio', '$categoria', '$area_util', '$area_total', '$salas', '$descricao_vagas', '$pavimentos', '$por_andar', '$sol_da_manha', '$interfone', '$sauna', '$portaria_permanente', '$elevador', '$porteiro_fisico', '$idade', '$armario_cozinha', '$armario_quartos', '$dce', '$piscina', '$salao_festas', '$hall_decorado', '$circuito_tv', '$area_verde', '$espaco_gourmet', '$corretor_id')") or die(mysql_error());

    }

    protected function saveIteracao($it){

        $this->conectaBanco();

        $id_corretor = $it['id_corretor'];
        $id_usuario = $it['id_usuario'];
        $message = $it['message'];
        $metodo = $it['metodo'];
        $data = $it['data'];

        return $query = mysql_query("INSERT INTO interacao (id_corretor, id_usuario, message, metodo, data) values ('$id_corretor', '$id_usuario', '$message', '$metodo', '$data')")or die(mysql_error());

    }

    protected function saveProfile($p){

        $this->conectaBanco();

        $pluspic = $p['pic'];
        $plusid = $p['pid'];
        $plusurl = $p['url'];
        $type = $p['type'];
        $name = $p['name'];
        $email = $p['email'];
        $email1 = $p['email1'];
        $email2 = $p['email2'];
        $fone1 =  $p['fone1'];
        $fone2 =  $p['fone2'];
        $fone3 =  $p['fone3'];
        $fone4 =  $p['fone4'];
        $fburl =   $p['fburl'];
        $instaurl =   $p['instaurl'];
        $lkd =   $p['lkd'];
        $street =   $p['street'];
        $numero =   $p['number'];
        $neighborhood =   $p['bairro'];
        $state = $p['state'];
        $country = $p['pais'];
        $zipcode = $p['cep'];
        $coverpic = $p['cover'];


        return $query = mysql_query ("INSERT INTO profile (pluspic, plusid, plusurl, type, name, email, email1, email2, fone1, fone2, fone3, fone4, fburl, instagramurl, linkedinurl, street, numero, neighborhood, state, country, zipcode, coverpic) values ('$pluspic', '$plusid','$plusurl','$type','$name','$email','$email1','$email2','$fone1','$fone2','$fone3','$fone4','$fburl','$instaurl','$lkd','$street','$numero','$neighborhood','$state','$country','$zipcode','$coverpic')") or die(mysql_error());

    }

    protected function saveRelacionamento($rel){

        $this->conectaBanco();

        $id_corretor = $rel['id_corretor'];
        $id_usuario =  $rel['id_usuario'];
        $status =  $rel['status'];
        $obs =  $rel['obs'];

        return $query = mysql_query("INSERT INTO relacionamento (id_corretor, id_usuario, status, obs) values ('$id_corretor', '$id_usuario', '$status', '$obs')") or die(mysql_error());

    }

    protected function saveFavorito($rel){

        $this->conectaBanco();

        $id_imovel = $rel['id_imovel'];
        $id_corretor = $rel['id_corretor'];
        $id_usuario =  $rel['id_usuario'];
        $status =  $rel['status'];
        $date =  $rel['date'];

        return $query = mysql_query("INSERT INTO favoritos (id_imovel, id_usuario, id_corretor, `date`, status) values ('$id_imovel', '$id_usuario', '$id_corretor', '$date','$status') ") or die(mysql_error());
        //$query = mysql_query("INSERT INTO favoritos (id_imovel) values ('1')");
    }

    //converter endereco em um objeto com latitude e longitude

    protected function getLongLat($endereco){

        $end = str_replace(" ", "+", $endereco);

        $url = "http://maps.googleapis.com/maps/api/geocode/json?address=".$end."&sensor=false";

        $json = file_get_contents($url);
        $data = json_decode($json, TRUE);


        return json_encode($data['results'][0]['geometry']['location']);

    }

}