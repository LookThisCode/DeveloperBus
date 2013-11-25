<?php
/*
 * User Meta and groups functions exitend includes/fields.php 
 */
require_once WPCF_EMBEDDED_INC_ABSPATH . '/usermeta.php';

/**
 * Returns HTML formatted AJAX activation link for usermeta.
 * 
 * @param type $group_id
 * @return type 
 */
function wpcf_admin_usermeta_get_ajax_activation_link($group_id) {
    return '<a href="' . admin_url('admin-ajax.php?action=wpcf_ajax&amp;'
                    . 'wpcf_action=activate_user_group&amp;group_id='
                    . $group_id . '&amp;wpcf_ajax_update=wpcf_list_ajax_response_'
                    . $group_id) . '&amp;_wpnonce=' . wp_create_nonce('activate_user_group')
            . '" class="wpcf-ajax-link" id="wpcf-list-activate-'
            . $group_id . '">'
            . __('Activate', 'wpcf') . '</a>';
}

/**
 * Returns HTML formatted AJAX deactivation link for usermeta.
 * @param type $group_id
 * @return type 
 */
function wpcf_admin_usermeta_get_ajax_deactivation_link($group_id) {
    return '<a href="' . admin_url('admin-ajax.php?action=wpcf_ajax&amp;'
                    . 'wpcf_action=deactivate_user_group&amp;group_id='
                    . $group_id . '&amp;wpcf_ajax_update=wpcf_list_ajax_response_'
                    . $group_id) . '&amp;_wpnonce=' . wp_create_nonce('deactivate_user_group')
            . '" class="wpcf-ajax-link" id="wpcf-list-activate-'
            . $group_id . '">'
            . __('Deactivate', 'wpcf') . '</a>';
}

