<?php
/*
Theme Name: GamEscola
Theme URI: 
Author: GamEscola - Hussani - Michelli - Tiago - Silvia
Author URI: https://plus.google.com/u/0/b/112977374414232772041/
Description: Aplicação online que visa promover a melhoria no ensino básico
Version: 0.1

*/

add_theme_support('post-thumbnails');

// RETIRA ADMINBAR DO WORDPRESS
add_filter('show_admin_bar', '__return_false');

// PERGUNTAS
add_action( 'init', 'perguntas_postType' );
function perguntas_postType() {
    $labels = array(
    'name' => _x('Perguntas', 'post type general name'),
    'singular_name' => _x('Perguntas', 'post type singular name'),
    'add_new' => _x('Adicionar Pergunta', 'Teste'),
    'add_new_item' => __('Adicionar Pergunta'),
    'edit_item' => __('Editar Pergunta'),
    'new_item' => __('Adicionar Pergunta'),
    'all_items' => __('Ver todas'),
    'view_item' => __('Ver todas'),
    'search_items' => __('Buscar...'),
    'not_found' =>  __('Nenhum item cadastrado até o momento.'),
    'not_found_in_trash' => __('A lixeira esta vazia.'),
    'parent_item_colon' => '',
    'menu_name' => 'Perguntas'
    );
    $args = array(
    'labels' => $labels,
    'public' => true,
    'publicly_queryable' => true,
    'show_ui' => true, 
    'show_in_menu' => true, 
    'query_var' => true,
    'rewrite' => true,
    'capability_type' => 'post',
    'has_archive' => true, 
    'hierarchical' => false,
    'menu_position' => 5,
    'supports' => array('title')
    );
    register_post_type('perguntas', $args );
}
add_action( 'init', 'create_taxonomy_perguntas_quiz' );
function create_taxonomy_perguntas_quiz() {
    register_taxonomy('perguntas_quiz', array( 'perguntas' ), array(
        'hierarchical' => true,
        'label' => 'Quizes',
        'show_ui' => true,
        'show_in_tag_cloud' => true,
        'query_var' => true,
        'rewrite' => array( 'slug' => 'quiz' ),
        )
    );
}


// MOOD
add_action( 'init', 'mood_postType' );
function mood_postType() {
    $labels = array(
    'name' => _x('Mood', 'post type general name'),
    'singular_name' => _x('Mood', 'post type singular name'),
    'add_new' => _x('Adicionar Mood', 'Teste'),
    'add_new_item' => __('Adicionar Mood'),
    'edit_item' => __('Editar Mood'),
    'new_item' => __('Adicionar Mood'),
    'all_items' => __('Ver todas'),
    'view_item' => __('Ver todas'),
    'search_items' => __('Buscar...'),
    'not_found' =>  __('Nenhum item cadastrado até o momento.'),
    'not_found_in_trash' => __('A lixeira esta vazia.'),
    'parent_item_colon' => '',
    'menu_name' => 'Moods'
    );
    $args = array(
    'labels' => $labels,
    'public' => true,
    'publicly_queryable' => true,
    'show_ui' => true, 
    'show_in_menu' => true, 
    'query_var' => true,
    'rewrite' => true,
    'capability_type' => 'post',
    'has_archive' => true, 
    'hierarchical' => false,
    'menu_position' => 5,
    'supports' => array('title')
    );
    register_post_type('mood', $args );
}


/********************* META BOX DEFINITIONS ***********************/

/**
 * Prefix of meta keys (optional)
 * Use underscore (_) at the beginning to make keys hidden
 * Alt.: You also can make prefix empty to disable it
 */
// Better has an underscore as last sign
$prefix = 'YOUR_PREFIX_';

global $meta_boxes;

$meta_boxes = array();

$meta_boxes[] = array(
	// Meta box id, UNIQUE per meta box. Optional since 4.1.5
	'id' => 'standard',
	// Meta box title - Will appear at the drag and drop handle bar. Required.
	'title' => 'Opções para Quiz',
	// Post types, accept custom post types as well - DEFAULT is array('post'). Optional.
	'pages' => array( 'perguntas' ),
	// Where the meta box appear: normal (default), advanced, side. Optional.
	'context' => 'normal',
	// Order of meta box: high (default), low. Optional.
	'priority' => 'high',
	// Auto save: true, false (default). Optional.
	'autosave' => true,

	'fields' => array(

		array(
			'name'             => 'Imagens para Quiz',
			'id'               => 'quiz_images',
			'type'             => 'plupload_image',
			'max_file_uploads' => 3,
		),
		
		array(
			'name' => 'Texto Auduvel',
			'desc' => 'Até 100 caracteres',
			'id'   => 'audible_text',
			'type' => 'textarea',
			'cols' => 20,
			'rows' => 3,
		),


	)
);

$meta_boxes[] = array(
	'id' => 'standard',
	'title' => __( 'Opções para Quiz', 'rwmb' ),
	'pages' => array( 'mood' ),
	'context' => 'normal',
	'priority' => 'high',
	'autosave' => true,
	'fields' => array(

		array(
			'name'  => 'ID usuário',
			'id'    => 'mood_id_user',
			'desc'  => 'ID # do usuário no banco de users WP',
			'type'  => 'text',
			'clone' => false,
		),
		
		array(
			'name'     => 'Mood',
			'id'       => 'mood',
			'type'     => 'select',
			// Array of 'value' => 'Label' pairs for select box
			'options'  => array(			
				'Cansado' => 'Cansado',
				'Triste' => 'Triste',
				'Feliz' => 'Feliz',
			),
			// Select multiple values, optional. Default is false.
			'multiple'    => false,
		),


	)
);

/********************* META BOX REGISTERING ***********************/

/**
 * Register meta boxes
 *
 * @return void
 */
function YOUR_PREFIX_register_meta_boxes()
{
	// Make sure there's no errors when the plugin is deactivated or during upgrade
	if ( !class_exists( 'RW_Meta_Box' ) )
		return;

	global $meta_boxes;
	foreach ( $meta_boxes as $meta_box )
	{
		new RW_Meta_Box( $meta_box );
	}
}
// Hook to 'admin_init' to make sure the meta box class is loaded before
// (in case using the meta box class in another plugin)
// This is also helpful for some conditionals like checking page template, categories, etc.
add_action( 'admin_init', 'YOUR_PREFIX_register_meta_boxes' );

// ADICIONA FIELD DE id DE CONTATO NO CADASTRO DE USERS DO WORDPRESS
function fb_add_custom_user_profile_fields( $user ) {
?>
   
    <table class="form-table">
        <tr>
            <th>
                <label for="pontos">Pontuação</label></th>
            <td>
                <input type="text" name="pontos" id="pontos" value="<?php echo esc_attr( get_the_author_meta( 'pontos', $user->ID ) ); ?>" class="regular-text" /><br />
            </td>
        </tr>
    </table>
<?php }
function fb_save_custom_user_profile_fields( $user_id ) {
    
    if ( !current_user_can( 'edit_user', $user_id ) )
        return FALSE;
    
    update_usermeta( $user_id, 'pontos', $_POST['pontos'] );
}
add_action( 'show_user_profile', 'fb_add_custom_user_profile_fields' );
add_action( 'edit_user_profile', 'fb_add_custom_user_profile_fields' );
add_action( 'personal_options_update', 'fb_save_custom_user_profile_fields' );
add_action( 'edit_user_profile_update', 'fb_save_custom_user_profile_fields' );



?>
