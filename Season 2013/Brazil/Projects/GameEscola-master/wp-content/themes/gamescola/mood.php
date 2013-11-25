<?php
require_once("../../../wp-config.php");
require_once("../../../wp-includes/wp-db.php");
require_once("../../../wp-includes/pluggable.php"); 

$current_user = wp_get_current_user();

// Create page sendEmail
    $my_post_mail = array(
      'post_title'    => 'Mood',
      'post_status'   => 'publish',
      'post_type'     => 'mood'
    );    
    $mail_id = wp_insert_post( $my_post_mail );
    add_post_meta( $mail_id, 'mood_id_user', $current_user->ID );
    add_post_meta( $mail_id, 'mood', $_GET['mood'] );

?>
