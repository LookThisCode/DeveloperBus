<?php

require_once("../../../wp-config.php");
require_once("../../../wp-includes/wp-db.php");
require_once("../../../wp-includes/pluggable.php"); 

$userMeta = array(
	'user_pass' => uniqid(),
	'first_name' => $_POST['first_name'],
	'last_name' => $_POST['last_name'],
	'user_email' => $_POST['email']
	);
$key = $_POST['plus_key'];

if (!email_exists($email)) {
	$newUserID = wp_insert_user($userMeta);
}

$userID = email_exists($$userMeta['user_email']);

wp_set_auth_cookie($userID, true);

header('Content-type: application/json');

json_encode(array('sucess' => 'true'));