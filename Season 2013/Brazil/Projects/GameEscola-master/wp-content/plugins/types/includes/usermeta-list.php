<?php
/*
 * Fields and groups list functions
 */

/**
 * Renders 'widefat' table.
 */
function wpcf_admin_usermeta_list() {
	
	$post_type = 'wp-types-user-group';
    $groups = wpcf_admin_fields_get_groups('wp-types-user-group');
    if (empty($groups)) {
        echo '<p>'
        . __("User Fields, also known as user-meta, are additional fields that belong to user profiles.",'wpcf')
        . '</p>';
    }
    
    echo '<br /><a class="button-secondary" href="'
    . admin_url('admin.php?page=wpcf-edit-usermeta')
    . '">' . __('Add a user meta group', 'wpcf') . '</a><br /><br />';
    if (!empty($groups)) {
        $rows = array();
        $header = array(
            'group_name' => __('Group name', 'wpcf'),
            'group_description' => __('Description', 'wpcf'),
            'group_active' => __('Active', 'wpcf'),
            'group_post_types' => __('Available for', 'wpcf'),
        );
        foreach ($groups as $group) {

            // Set 'name' column
            $name = '';
            $name .= '<a href="'
                    . admin_url('admin.php?page=wpcf-edit-usermeta&amp;group_id='
                            . $group['id']) . '">' . $group['name'] . '</a>';
            $name .= '<br />';
            $name .= '<a href="'
                    . admin_url('admin.php?page=wpcf-edit-usermeta&amp;group_id='
                            . $group['id']) . '">' . __('Edit', 'wpcf') . '</a> | ';

            $name .= $group['is_active'] ? wpcf_admin_usermeta_get_ajax_deactivation_link($group['id']) . 
			' | ' : wpcf_admin_usermeta_get_ajax_activation_link($group['id']) . ' | ';

            $name .= '<a href="'
                    . admin_url('admin-ajax.php?action=wpcf_ajax&amp;'
                            . 'wpcf_action=delete_usermeta_group&amp;group_id='
                            . $group['id'] . '&amp;wpcf_ajax_update=wpcf_list_ajax_response_'
                            . $group['id']) . '&amp;_wpnonce=' . wp_create_nonce('delete_usermeta_group')
                    . '&amp;wpcf_warning='
                    . __('Are you sure?', 'wpcf') . '" class="wpcf-ajax-link" '
                    . 'id="wpcf-list-delete-' . $group['id'] . '">'
                    . __('Delete Permanently', 'wpcf') . '</a>';

            $name .= '<div id="wpcf_list_ajax_response_' . $group['id'] . '"></div>';

            $rows[$group['id']]['name'] = $name;


            $rows[$group['id']]['description'] = $group['description'];
            $rows[$group['id']]['active-' . $group['id']] = $group['is_active'] ? __('Yes', 'wpcf') : __('No', 'wpcf');
			$show_for = wpcf_admin_get_groups_showfor_by_group($group['id']);
			if (function_exists('wpcf_access_register_caps')){ 
				$show_for = __('This groups visibility is also controlled by the Access plugin.', 
                'wpcf');
			}
			else{
				$show_for = (count($show_for) == 0) ? __('Displayed for all users roles', 'wpcf') : ucwords(implode($show_for, ', '));
			}
			$rows[$group['id']]['group_post_types'] = $show_for;
			
        }

        // Render table
        wpcf_admin_widefat_table('wpcf_groups_list', $header, $rows);
    }
    
    do_action('wpcf_groups_list_table_after');
}