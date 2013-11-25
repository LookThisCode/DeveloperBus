<?php
//error_reporting(E_ALL);
//ini_set('display_errors', '1');

$b = new ContinuadaJson();
echo $b->imo();
/*
if(isset($_GET['choice']))
        $bingo = $_GET['choice'];
else
        $bingo = null;

switch ($bingo) {
        case 'imo':
        echo $b->imo();
        break;
                //quem se increveu mas não necessariamente foi
        case 'getInscritos':
        echo $b->fetchAllInscritos($_GET['id']);
        break;
}
*/


class ContinuadaJson{

        public function conectaBanco(){

                $server='mysql02.cromg.org.br';
                $user='cromg1';
                $senha='croblong';
                $bd='cromg1';
                $conexao=mysql_connect($server,$user,$senha) or die (mysql_error());
                mysql_set_charset('utf8',$conexao);
                mysql_select_db($bd,$conexao) or die (mysql_error());

        }

        /*
         * Pega todos os cursos caso passe ID como parâmetro $id pegará um              curso determinado
         *
        */
        public function imo(){

                $this->conectaBanco();
            	$result = mysql_query("SELECT * FROM user");

            	while($row = mysql_fetch_array($result)){
                       var_dump($row);
                }


        }
}