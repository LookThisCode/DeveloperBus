<?php
/*
 * Validation class.
 */

/**
 * Validation class.
 * 
 * @since Types 1.1.5
 * @package Types
 * @subpackage Validation
 * @version 0.1.1
 * @category core
 * @author srdjan <srdjan@icanlocalize.com>
 */
class WPCF_Validation
{

    /**
     * Helper object.
     * 
     * @var type object
     */
    var $_helper;

    function __construct() {
        $this->_helper = new stdClass();
        add_filter( 'wpcf_field', array($this, 'filter_field') );
    }

    /**
     * Sets internal helpers.
     */
    function _set_helpers() {
        require_once WPCF_EMBEDDED_ABSPATH . '/classes/validation/javascript.php';
        if ( empty( $this->helper->js ) ) {
            $this->_helper->js = new WPCF_Validation_Javascript();
        }
    }

    /**
     * Renders JS validation for fields.
     * 
     * @param type $selector
     * @return type
     */
    function js_fields( $selector ) {
        $this->_set_helpers();
        return $this->_helper->js->fields_js( $selector );
    }

    /**
     * Filters each field.
     * 
     * Uses 'wpcf_field' hook. Filters field settings.
     * 
     * @param type $field
     * @return int
     */
    function filter_field( $field ) {
        if ( $field['type'] == 'date' ) {
            if ( !fields_date_timestamp_neg_supported() ) {
                if ( !isset( $field['data']['validate'] )
                        || !is_array( $field['data']['validate'] ) ) {
                    $field['data']['validate'] = array();
                }
                $field['data']['validate']['negativeTimestamp'] = array(
                    'active' => 1,
                    'value' => 'true',
                    'message' => wpcf_admin_validation_messages( 'negativeTimestamp' ),
                );
            }
        }
        return $field;
    }

}