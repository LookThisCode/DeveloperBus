<?php
/*
 * Loader class
 */

/**
 * Loader Class
 * 
 * @since Types 1.2
 * @package Types
 * @subpackage Classes
 * @version 0.2
 * @category Loader
 * @author srdjan <srdjan@icanlocalize.com>
 */
class WPCF_Loader
{

    /**
     * Settings
     * @var array 
     */
    private static $__settings = array();

    public static function init( $settings = array() ) {
        self::$__settings = (array) $settings;
        self::__registerScripts();
        self::__registerStyles();
        self::__toolset();
        add_action( 'admin_print_scripts',
                array('WPCF_Loader', 'renderJsSettings'), 5 );
    }

    /**
     * Register scripts.
     */
    private static function __registerScripts() {
        wp_register_script( 'types', WPCF_EMBEDDED_RES_RELPATH . '/js/basic.js',
                array('jquery'), WPCF_VERSION, true );
        wp_register_script( 'types-knockout',
                WPCF_EMBEDDED_RES_RELPATH . '/js/knockout-2.2.1.js',
                array('jquery'), WPCF_VERSION, true );
        if ( !wp_script_is( 'toolset-colorbox', 'registered' ) ) {
            wp_register_script( 'toolset-colorbox',
                    WPCF_EMBEDDED_RES_RELPATH . '/js/jquery.colorbox-min.js',
                    array('jquery'), WPCF_VERSION, true );
        }
        wp_register_script( 'types-utils',
                WPCF_EMBEDDED_RES_RELPATH . '/js/utils-min.js', array('jquery'),
                WPCF_VERSION, true );
        wp_register_script( 'types-wp-views',
                WPCF_EMBEDDED_RES_RELPATH . '/js/wp-views.js', array('jquery'),
                WPCF_VERSION, true );
        wp_register_script( 'types-conditional',
                WPCF_EMBEDDED_RES_RELPATH . '/js/conditional.js', array('types-utils'),
                WPCF_VERSION, true );
//        wp_register_script( 'types-jquery-validation',
//                WPCF_EMBEDDED_RES_RELPATH . '/js/jquery-form-validation/jquery.validate-1.11.1.min.js',
//                array('jquery'), WPCF_VERSION, true );
//        wp_register_script( 'types-jquery-validation-additional',
//                WPCF_EMBEDDED_RES_RELPATH . '/js/jquery-form-validation/additional-methods-1.11.1.min.js',
//                array('types-jquery-validation'), WPCF_VERSION, true );
//        wp_register_script( 'types-js-validation',
//                WPCF_EMBEDDED_RES_RELPATH . '/js/jquery-form-validation/types.js',
//                array('types-jquery-validation-additional'), WPCF_VERSION, true );
    }

    /**
     * Register styles.
     */
    private static function __registerStyles() {
        wp_register_style( 'types',
                WPCF_EMBEDDED_RES_RELPATH . '/css/basic.css', array(),
                WPCF_VERSION );
        if ( !wp_style_is( 'toolset-colorbox', 'registered' ) ) {
            wp_register_style( 'toolset-colorbox',
                    WPCF_EMBEDDED_RES_RELPATH . '/css/colorbox.css', array(),
                    WPCF_VERSION );
        }
        if ( !wp_style_is( 'toolset-font-awesome', 'registered' ) ) {
            wp_register_style( 'toolset-font-awesome',
                    WPCF_EMBEDDED_RES_RELPATH . '/css/font-awesome/css/font-awesome.min.css',
                    array('admin-bar', 'wp-admin', 'buttons', 'media-views'),
                    WPCF_VERSION );
        }
    }

    /**
     * Returns HTML formatted output.
     * 
     * @param string $view
     * @param mixed $data
     * @return string
     */
    public static function view( $view, $data = array() ) {
        $file = WPCF_EMBEDDED_ABSPATH . '/views/'
                . strtolower( strval( $view ) ) . '.php';
        if ( !file_exists( $file ) ) {
            return '<code>missing_view</code>';
        }
        ob_start();
        include $file;
        $output = ob_get_contents();
        ob_get_clean();

        return apply_filters( 'wpcf_get_view', $output, $view, $data );
    }

    /**
     * Returns HTML formatted output.
     * 
     * @param string $template
     * @param mixed $data
     * @return string
     */
    public static function template( $template, $data = array() ) {
        $file = WPCF_EMBEDDED_ABSPATH . '/views/templates/'
                . strtolower( strval( $template ) ) . '.tpl.php';
        if ( !file_exists( $file ) ) {
            return '<code>missing_template</code>';
        }
        ob_start();
        include $file;
        $output = ob_get_contents();
        ob_get_clean();

        return apply_filters( 'wpcf_get_template', $output, $template, $data );
    }

    /**
     * Loads model.
     * 
     * @param string $template
     * @param mixed $data
     * @return string
     */
    public static function loadModel( $model ) {
        $file = WPCF_EMBEDDED_ABSPATH . '/models/'
                . strtolower( strval( $model ) ) . '.php';
        if ( !file_exists( $file ) ) {
            return new WP_Error( 'types-loader-model', 'missing model ' . $model );
        }
        require_once $file;
    }

    /**
     * Loads class.
     * 
     * @param string $template
     * @param mixed $data
     * @return string
     */
    public static function loadClass( $class ) {
        $file = WPCF_EMBEDDED_ABSPATH . '/classes/'
                . strtolower( strval( $class ) ) . '.php';
        if ( !file_exists( $file ) ) {
            return new WP_Error( 'types-loader-class', 'missing class ' . $class );
        }
        require_once $file;
    }

    /**
     * Loads include.
     * 
     * @param string $template
     * @param mixed $data
     * @return string
     */
    public static function loadInclude( $name, $mode = 'embedded' ) {
        $path = $mode == 'plugin' ? WPCF_ABSPATH : WPCF_EMBEDDED_ABSPATH;
        $file = $path . '/includes/' . strtolower( strval( $name ) ) . '.php';
        if ( !file_exists( $file ) ) {
            return new WP_Error( 'types-loader-include', 'missing include ' . $name );
        }
        require_once $file;
    }

    /**
     * Adds JS settings.
     * 
     * @staticvar array $settings
     * @param type $id
     * @param type $setting
     */
    public static function addJsSetting( $id, $setting = '' ) {
        self::$__settings[$id] = $setting;
    }

    /**
     * Renders JS settings.
     */
    public static function renderJsSettings() {
        $settings = (array) self::$__settings;
        $settings['wpnonce'] = wp_create_nonce( '_typesnonce' );
        $settings['cookiedomain'] = COOKIE_DOMAIN;
        $settings['cookiepath'] = COOKIEPATH;
        echo '
        <script type="text/javascript">
            //<![CDATA[
            var types = ' . json_encode( $settings ) . ';
            //]]>
        </script>';
    }

    /**
     * Toolset loading.
     */
    private static function __toolset() {
        // Views
        if ( defined( 'WPV_VERSION' ) ) {
            self::loadClass( 'wpviews' );
            WPCF_WPViews::init();
        }
    }

}