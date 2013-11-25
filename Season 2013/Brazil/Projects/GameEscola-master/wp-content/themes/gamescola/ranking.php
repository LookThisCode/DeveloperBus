<?php
require_once("../../../wp-config.php");
require_once("../../../wp-includes/wp-db.php");
require_once("../../../wp-includes/pluggable.php"); 

 $blogusers = get_users();
 $users = array();
    foreach ($blogusers as $user) {

	$all_meta_for_user = get_user_meta( $user->ID );
	$users[$user->ID] = $all_meta_for_user['pontos'][0];
    }
    arsort($users);
    $ranking = array();
    foreach ($users as $user_id => $value) {
	$user = get_user_by( 'id', $user_id );
	$src = preg_match("/src='(.*?)'/i",get_avatar( $user->ID, 145),$match);
	$ranking[] = array(
		'name' => $user->first_name . ' ' . $user->last_name,
		'avatar' => $match[1]
		);		
    }
    
header_remove();
header('Content-type: application/json');
echo json_encode($ranking);



?>
