<?php
//ini_set('display_errors', 'On');
//error_reporting(E_ALL | E_STRICT);

        // Connect to test database
        //$conn = mysql_connect(":/cloudsql/gcdc2013-partyme:treko", "root", "minguado");
        //$db = mysql_select_db("treko",$conn);
        

//        var_dump($db);
//        echo "123";


require_once '../vendor/SplClassLoader.php';

$classLoader = new SplClassLoader('VILLA','../vendor');
$classLoader->register();

$classLoader = new SplClassLoader('FACEBOOK','../vendor');
$classLoader->register();

$classLoader = new SplClassLoader('GPLUS','../vendor');
$classLoader->register();

$classLoader = new SplClassLoader('YOUTUBE','../vendor');
$classLoader->register();

$classLoader = new SplClassLoader('MAPS','../vendor');
$classLoader->register();

$classLoader = new SplClassLoader('INSTAGRAM','../vendor');
$classLoader->register();

$classLoader = new SplClassLoader('App','../');
$classLoader->register();

$bootstrap = new \App\Init;