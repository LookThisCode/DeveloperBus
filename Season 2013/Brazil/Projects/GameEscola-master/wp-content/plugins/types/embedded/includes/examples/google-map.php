<?php
/*

  This is example how to add simple Google Maps field type.
  Most of functions are called automatically from Types plugin.
  Functions naming conventions are:

  - For basic type data (required) callback
  wpcf_fields_$myfieldname()

  Optional

  - Group form data callback
  wpcf_fields_$myfieldname_insert_form()

  - Post edit page form callback
  wpcf_fields_$myfieldname_meta_box_form()

  - Editor popup callback
  wpcf_fields_$myfieldname_editor_callback()

  - View function callback
  wpcf_fields_$myfieldname_view()

 */

// Add registration hook
add_filter( 'types_register_fields', 'my_types' );

/**
 * Register custom post type on 'types_register_fields' hook.
 * 
 * @param array $fields
 * @return type
 */
function my_types( $fields ) {
    $fields['google_map'] = wpcf_fields_google_map();
    return $fields;
}

/**
 * Define field.
 * 
 * @return type
 */
function wpcf_fields_google_map() {
    return array(
        'path' => __FILE__,
        'id' => 'google_map',
        'title' => __( 'Google Map', 'wpcf' ),
        'description' => __( 'This is additional field', 'wpcf' ),
        /*
         * Validation
         * 
         * TODO Elaborate on this
         * Add examples for various usage (review needed)
         */
        'validate' => array('required'),
        /*
         * 
         * Inherited post type
         * 
         * Use to extend some existing field type.
         * Example:
         * Google Map extends Textfield.
         * To override Textfield settings you need to overwrite
         * basic form elements.
         * Image field extends File field...
         * 
         * Use $form['name'] to overwritte first element
         * (in this case main Textfield element).
         * 
         * Do not use unless really needed.
         * Define your custom field completely.
         */
//        'inherited_field_type' => 'textfield',
        /*
         * 
         * 
         * 
         * 
         * 
         * 
         * Possible (optional) parameters
         */
        // Additional JS on post edit page
        'meta_box_js' => array(// Add JS when field is active on post edit page
            'wpcf-jquery-fields-my-field' => array(
                'inline' => 'wpcf_fields_google_map_meta_box_js_inline', // This calls function that renders JS
                'deps' => array('jquery'), // (optional) Same as WP's enqueue_script() param
                'in_footer' => true, // (optional) Same as WP's enqueue_script() param
            ),
            'wpcf-jquery-fields-my-field' => array(
                'src' => get_stylesheet_directory_uri() . '/js/my-field.js', // This will load JS file
            ),
        ),
        // Additional CSS on post edit page
        'meta_box_css' => array(
            'wpcf-jquery-fields-my-field' => array(
                'src' => get_stylesheet_directory_uri() . '/css/my-field.css', // or inline function 'inline' => $funcname
                'deps' => array('somecss'), // (optional) Same as WP's enqueue_style() param
            ),
        ),
        // Additional JS on group edit page
        'group_form_js' => array(// Add JS when field is active on post edit page
            'wpcf-jquery-fields-my-field' => array(
                'inline' => 'wpcf_fields_google_map_group_form_js_inline', // This calls function that renders JS
                'deps' => array('jquery'), // (optional) Same as WP's enqueue_script() param
                'in_footer' => true, // (optional) Same as WP's enqueue_script() param
            ),
            'wpcf-jquery-fields-my-field' => array(
                'src' => get_stylesheet_directory_uri() . '/js/my-field.js', // This will load JS file
            ),
        ),
        // Additional CSS on post edit page
        'group_form_css' => array(
            'wpcf-jquery-fields-my-field' => array(
                'src' => get_stylesheet_directory_uri() . '/css/my-field.css', // or inline function 'inline' => $funcname
                'deps' => array('somecss'), // (optional) Same as WP's enqueue_style() param
            ),
        ),
        // override editor popup link (you must then load JS function that will process it)
//        'editor_callback' => 'wpcfFieldsMyFieldEditorCallback(\'%s\')', // %s will inject field ID
        // meta key type
        'meta_key_type' => 'INT',
        // Required WP version check
        'wp_version' => '3.3',
    );
}

/**
 * Types Group edit screen form.
 * 
 * Here you can specify all additional group form data if nedded,
 * it will be auto saved to field 'data' property.
 * 
 * @return string
 */
function wpcf_fields_google_map_insert_form() {
    $form['additional'] = array(
        '#type' => 'textfield',
        '#description' => 'Add some comment',
        '#name' => 'comment',
    );
    return $form;
}

/**
 * Overrides form output in meta box on post edit screen.
 */
function wpcf_fields_google_map_meta_box_form( $data ) {
    $form['name'] = array(
        '#name' => 'wpcf[' . $data['slug'] . ']', // Set this to override default output
        '#type' => 'textfield',
        '#title' => __( 'Add Google Map coordinates', 'wpcf' ),
        '#description' => __( 'Your input should look something like "41.934146,12.455821"',
                'wpcf' )
    );
    return $form;
}

/**
 * Adds editor popup callnack.
 * 
 * This form will be showed in editor popup
 */
function wpcf_fields_google_map_editor_callback() {
    wp_enqueue_style( 'wpcf-fields-google_map',
            WPCF_EMBEDDED_RES_RELPATH . '/css/basic.css', array(), WPCF_VERSION );
    wp_enqueue_script( 'jquery' );

    // Get field
    $field = wpcf_admin_fields_get_field( $_GET['field_id'] );
    if ( empty( $field ) ) {
        _e( 'Wrong field specified', 'wpcf' );
        die();
    }
    $last_settings = wpcf_admin_fields_get_field_last_settings( $_GET['field_id'] );
    if ( isset( $_POST['_wpnonce_wpcf_form'] ) && wp_verify_nonce( $_POST['_wpnonce_wpcf_form'],
                    'wpcf-form' ) ) {
        add_action( 'admin_head_wpcf_ajax',
                'wpcf_fields_google_map_editor_submit' );
    }
    wpcf_admin_ajax_head( __( 'Insert Google Map', 'wpcf' ) );

    ?>
    <form method="post" action="">
        <h2><?php _e( 'Google Maps' ); ?></h2>
        <?php _e( 'Specify width and height for Google Map:' ); ?><br /><br />
        <?php
        wp_nonce_field( 'wpcf-form', '_wpnonce_wpcf_form' );

        ?>
        <input type="text" name="width" value="<?php echo isset( $last_settings['width'] ) ? $last_settings['width'] : '425'; ?>" />
        <br />
        <input type="text" name="height" value="<?php echo isset( $last_settings['height'] ) ? $last_settings['height'] : '350'; ?>" />
        <br /><br /><input type="submit" class="button-primary" value="<?php _e( 'Insert Google Map' ); ?>" />
    </form>
    <?php
    wpcf_admin_ajax_footer();
}

/**
 * Processes editor popup submit
 */
function wpcf_fields_google_map_editor_submit() {

    // Get field
    $field = wpcf_admin_fields_get_field( strval( $_GET['field_id'] ) );

    if ( !empty( $field ) ) {
        $add = '';

        // Add parameters
        if ( !empty( $_POST['width'] ) ) {
            $add .= ' width="' . strval( $_POST['width'] ) . '"';
        }
        if ( !empty( $_POST['height'] ) ) {
            $add .= ' height="' . strval( $_POST['height'] ) . '"';
        }

        // Generate shortcode
        $shortcode = wpcf_fields_get_shortcode( $field, $add );

        // Save settings
        wpcf_admin_fields_save_field_last_settings( $_GET['field_id'], $_POST );

        // Trigger inserting shortcode in parent editor
        echo editor_admin_popup_insert_shortcode_js( $shortcode );
    }
    die();
}

/**
 * Renders view
 * 
 * Useful $data:
 * $data['field_value'] - Value of custom field
 * 
 * @param array $data
 */
function wpcf_fields_google_map_view( $data ) {
    $data['width'] = !empty( $data['width'] ) ? $data['width'] : 425;
    $data['height'] = !empty( $data['height'] ) ? $data['height'] : 350;
    return '<iframe width="' . $data['width'] . '" height="' . $data['height']
            . '" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="http://maps.google.com/maps?q='
            . $data['field_value']
            . '&amp;num=1&amp;vpsrc=0&amp;hl=en&amp;ie=UTF8&amp;t=m&amp;z=14&amp;ll='
            . $data['field_value']
            . '&amp;output=embed"></iframe><br /><small><a href="http://maps.google.com/maps?q='
            . $data['field_value']
            . '&amp;num=1&amp;vpsrc=0&amp;hl=en&amp;ie=UTF8&amp;t=m&amp;z=14&amp;ll='
            . $data['field_value']
            . '&amp;source=embed" style="color:#0000FF;text-align:left">'
            . __( 'View Larger Map', 'wpcf' )
            . '</a></small><br />';
    }

