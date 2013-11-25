<?php
//add_action('wpcf_relationship_save_child', 'wpcf_fields_checkbox_save_check',
//        10, 3);
// Trigger after main hook
add_action( 'save_post', 'wpcf_fields_checkbox_save_check', 15, 3 );

/**
 * Register data (called automatically).
 * 
 * @return type 
 */
function wpcf_fields_checkbox() {
    return array(
        'id' => 'wpcf-checkbox',
        'title' => __( 'Checkbox', 'wpcf' ),
        'description' => __( 'Checkbox', 'wpcf' ),
        'validate' => array('required'),
        'meta_key_type' => 'BINARY',
    );
}

/**
 * Form data for post edit page.
 * 
 * @param type $field 
 */
function wpcf_fields_checkbox_meta_box_form( $field, $field_object ) {
    global $wpcf;
    $checked = false;
    $field['data']['set_value'] = stripslashes( $field['data']['set_value'] );
    if ( $field['value'] == $field['data']['set_value'] ) {
        $checked = true;
    }
    // If post is new check if it's checked by default
    global $pagenow;
    if ( $pagenow == 'post-new.php' && !empty( $field['data']['checked'] ) ) {
        $checked = true;
    }
    // This means post is new
    if ( !isset( $field_object->post->ID ) ) {
        $field_object->post = (object) array('ID' => 0);
    }
    return array(
        '#type' => 'checkbox',
        '#value' => $field['data']['set_value'],
        '#default_value' => $checked,
        '#after' => '<input type="hidden" name="_wpcf_check_checkbox['
        . $field_object->post->ID . '][' . $field_object->slug
        . ']" value="1" />',
    );
}

/**
 * Editor callback form.
 */
function wpcf_fields_checkbox_editor_callback( $field, $settings ) {

    $value_not_selected = '';
    $value_selected = '';

    if ( isset( $field['data']['display_value_not_selected'] ) ) {
        $value_not_selected = $field['data']['display_value_not_selected'];
    }
    if ( isset( $field['data']['display_value_selected'] ) ) {
        $value_selected = $field['data']['display_value_selected'];
    }

    $data = array_merge( array(
        'selected' => WPCF_Editor::sanitizeParams( $value_selected ),
        'not_selected' => WPCF_Editor::sanitizeParams( $value_not_selected ),
            ), $settings );

    return array(
        'supports' => array('style'),
        'tabs' => array(
            'display' => array(
                'menu_title' => __( 'Display', 'wpcf' ),
                'title' => __( 'Display', 'wpcf' ),
                'content' => WPCF_Loader::template( 'editor-modal-checkbox',
                        $data ),
            )
        ),
    );
}

/**
 * Editor callback form submit.
 */
function wpcf_fields_checkbox_editor_submit( $data, $field, $context ) {
    $add = '';
    $types_attr = 'field';
    if ( $context == 'usermeta' ) {
        $add .= wpcf_get_usermeta_form_addon_submit();
        $types_attr = 'usermeta';
    }

    if ( $data['display'] == 'value' ) {

        $checked_add = $add . ' state="checked"';
        $unchecked_add = $add . ' state="unchecked"';

        if ( $context == 'usermeta' ) {
            $shortcode_checked = wpcf_usermeta_get_shortcode( $field,
                    $checked_add, $data['selected'] );
            $shortcode_unchecked = wpcf_usermeta_get_shortcode( $field,
                    $unchecked_add, $data['not_selected'] );
        } else {
            $shortcode_checked = wpcf_fields_get_shortcode( $field,
                    $checked_add, $data['selected'] );
            $shortcode_unchecked = wpcf_fields_get_shortcode( $field,
                    $unchecked_add, $data['not_selected'] );
        }
        $shortcode = $shortcode_checked . $shortcode_unchecked;
    } else {
        if ( $context == 'usermeta' ) {
            $shortcode = wpcf_usermeta_get_shortcode( $field, $add );
        } else {
            $shortcode = wpcf_fields_get_shortcode( $field, $add );
        }
    }

    return $shortcode;

}

/**
 * View function.
 * 
 * @param type $params 
 */
function wpcf_fields_checkbox_view( $params ) {
    $output = '';
    $option_name = 'wpcf-fields';
    if ( isset( $params['usermeta'] ) && !empty( $params['usermeta'] ) ) {
        $option_name = 'wpcf-usermeta';
    }
    if ( isset( $params['option_name'] ) ) {
        $option_name = $params['option_name'];
    }
    if ( isset( $params['state'] )
            && $params['state'] == 'unchecked'
            && empty( $params['field_value'] ) ) {
        if ( empty( $params['#content'] ) ) {
            return '__wpcf_skip_empty';
        }
        return htmlspecialchars_decode( $params['#content'] );
    } else if ( isset( $params['state'] ) && $params['state'] == 'unchecked' ) {
        return '__wpcf_skip_empty';
    }

    if ( isset( $params['state'] ) && $params['state'] == 'checked' && !empty( $params['field_value'] ) ) {
        if ( empty( $params['#content'] ) ) {
            return '__wpcf_skip_empty';
        }
        return htmlspecialchars_decode( $params['#content'] );
    } else if ( isset( $params['state'] ) && $params['state'] == 'checked' ) {
        return '__wpcf_skip_empty';
    }
    if ( !empty( $params['#content'] )
            && !empty( $params['field_value'] ) ) {
        return htmlspecialchars_decode( $params['#content'] );
    }

    // Check if 'save_empty' is yes and if value is 0 - set value to empty string
    if ( isset( $params['field']['data']['save_empty'] )
            && $params['field']['data']['save_empty'] == 'yes'
            && $params['field_value'] == '0' ) {
        $params['field_value'] = '';
    }

    if ( $params['field']['data']['display'] == 'db' && $params['field_value'] != '' ) {
        $field = wpcf_fields_get_field_by_slug( $params['field']['slug'],
                $option_name );
        $output = $field['data']['set_value'];

        // Show the translated value if we have one.
        $output = wpcf_translate( 'field ' . $field['id'] . ' checkbox value',
                $output );
    } else if ( $params['field']['data']['display'] == 'value'
            && $params['field_value'] != '' ) {
        if ( !empty( $params['field']['data']['display_value_selected'] ) ) {
            $output = $params['field']['data']['display_value_selected'];
            $output = wpcf_translate( 'field ' . $params['field']['id'] . ' checkbox value selected',
                    $output );
        }
    } else if ( $params['field']['data']['display'] == 'value'
        && !empty( $params['field']['data']['display_value_not_selected'] ) ) {
        $output = $params['field']['data']['display_value_not_selected'];
        $output = wpcf_translate( 'field ' . $params['field']['id'] . ' checkbox value not selected', $output );
    } else {
        return '__wpcf_skip_empty';
    }

    return $output;
}

/**
 * Check if checkbox is submitted.
 * 
 * Currently used on Relationship saving. May be expanded to general code.
 * 
 * @param type $post_id
 */
function wpcf_fields_checkbox_save_check( $post_id ) {

    $meta_to_unset = array();
    $cf = new WPCF_Field();

    /*
     * 
     * We hve several calls on this:
     * 1. Saving post with Update
     * 2. Saving all children
     * 3. Saving child
     */

    $mode = 'save_main';
    if ( defined( 'DOING_AJAX' ) ) {
        if ( isset( $_GET['wpcf_action'] )
                && $_GET['wpcf_action'] == 'pr_save_all' ) {
            $mode = 'save_all';
        } else if ( isset( $_GET['wpcf_action'] )
                && $_GET['wpcf_action'] == 'pr_save_child_post' ) {
            $mode = 'save_child';
        }
    }

    // See if any marked for checking
    if ( isset( $_POST['_wpcf_check_checkbox'] ) ) {

        // Loop and search in $_POST
        foreach ( $_POST['_wpcf_check_checkbox'] as $child_id => $slugs ) {
            foreach ( $slugs as $slug => $true ) {

                $cf->set( $child_id, $cf->__get_slug_no_prefix( $slug ) );

                // First check main post
                if ( $mode == 'save_main'
                        && intval( $child_id ) == wpcf_get_post_id() ) {
                    if ( !isset( $_POST['wpcf'][$cf->cf['slug']] ) ) {
                        $meta_to_unset[intval( $child_id )][$cf->slug] = true;
                    }
                    continue;
                }

                // If new post
                if ( $mode == 'save_main' && $child_id == 0 ) {
                    if ( !isset( $_POST['wpcf'][$cf->cf['slug']] ) ) {
                        $meta_to_unset[$post_id][$cf->slug] = true;
                    }
                    continue;
                }
                /*
                 * 
                 * Relationship check
                 */
                if ( $mode == 'save_main' ) {
                    if ( !isset( $_POST['wpcf'][$cf->cf['slug']] ) ) {
                        $meta_to_unset[$post_id][$cf->slug] = true;
                    }
                    continue;
                } else if ( !empty( $_POST['wpcf_post_relationship'] ) ) {
                    foreach ( $_POST['wpcf_post_relationship'] as $_parent => $_children ) {
                        foreach ( $_children as $_child_id => $_slugs ) {
                            if ( !isset( $_slugs[$slug] ) ) {
                                $meta_to_unset[$_child_id][$cf->slug] = true;
                            }
                        }
                    }
                }
            }
        }
    }

    // After collected - delete them
    foreach ( $meta_to_unset as $child_id => $slugs ) {
        foreach ( $slugs as $slug => $true ) {
            $cf->set( $child_id, $cf->__get_slug_no_prefix( $slug ) );
            if ( $cf->cf['data']['save_empty'] != 'no' ) {
                update_post_meta( $child_id, $slug, 0 );
            } else {
                delete_post_meta( $child_id, $slug );
            }
        }
    }
}