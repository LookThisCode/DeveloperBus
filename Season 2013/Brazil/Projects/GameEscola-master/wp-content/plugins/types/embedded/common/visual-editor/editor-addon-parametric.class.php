<?php
if ( file_exists( dirname(__FILE__) . '/editor-addon-generic.class.php') && !class_exists( 'Editor_addon_parametric' )  ) {

    require_once( dirname(__FILE__) . '/editor-addon-generic.class.php' );
    
    
    class Editor_addon_parametric extends Editor_addon_generic
    { 
    	
    	public function __construct($name, $button_text, $plugin_js_url,
            $media_button_image = '' )
    	{
	    	parent::__construct($name, $button_text, $plugin_js_url,
            $media_button_image);
            
            add_action('wp_ajax_' . $this->name, array( $this, 'send_data_to_parametric_form'));
	    	
	    	add_action('admin_head', array($this, 'init'));

    	}
    	
    	/**
    	 * init function.
    	 * 
    	 * @access public
    	 * @return void
    	 */
    	public function init()
    	{
    		global $pagenow;
    		
    		if( $pagenow == 'admin.php' && isset( $_GET['page'] ) && $_GET['page'] == 'views-editor'  )
    		{
	    		$this->initStatics(  );
	    	
	    		add_filter('wpv_meta_html_add_form_button_new', array($this, 'add_form_button') );
		    
	    		add_short_codes_to_js(array(''), $this);

    		}
	    	
	    }
    	
    	
    	/**
    	 * get_taxes function.
    	 * 
    	 * @access private
    	 * @param array $object_type
    	 * @param string $out
    	 * @return array
    	 */
    	private function getTaxonomiesFromPostTypes( $object_types, $out = 'names' )
    	{
    		return get_object_taxonomies( $object_types, $out );
    	}
    	
    	
    	/**
    	 * getTaxonomiesArray function.
    	 * 
    	 * @access public
    	 * @param mixed $object_types
    	 * @param string $out
    	 * @return array
    	 */
    	public function getPostTypesTaxonomies( $object_types, $out = 'names' )
    	{
	    	if( is_array( $object_types ) )
	    	{
	    		$taxonomies = $this->getTaxonomiesFromPostTypes( $object_types, $out );
	    		
	    		//Clean up the array from values we do not want, array_diff makes a mess in the json so do it lile so
	    		$exclude = array('post_format');
	    		$newArr = array_diff($taxonomies, $exclude);
	    		
	    			    		
		    	return  array_values( array_unique( $newArr ) ); 
		    			 
		    		
	    	}
	    	else
	    	{
	    		
		    	return array( 'error' => "Parameter should be an array " .  __METHOD__, 'wpv-views' );
	    	}
    	}
    	    	
    	/**
    	 * get_postmetakeys_by_post_type function.
    	 * 
    	 * @access private
    	 * @param mixed $type
    	 * @return array
    	 */
    	private function get_postmetakeys_by_post_type( $type )
    	{
    		if( !$type ) return array('error' => __('No types to query...make sure you made a selection ' . __METHOD__, 'wpv-views') );
    		
	    	global $wpdb;
	    	
	    	$arr = array();
	    	
	    	//You loop through wp_postmeta by post_id of a given post type, so give keys only once
		    $results = $wpdb->get_results($wpdb->prepare(
		        "SELECT DISTINCT meta_key FROM {$wpdb->postmeta} WHERE post_id IN
		        (SELECT ID FROM {$wpdb->posts} WHERE post_type = %s)", $type
		    ) );
		    
		    
		    foreach( $results as $res )
		    {
		    	// push only if it is not there, we want to be safe
		    	if( !in_array( $res->meta_key, $arr ) ) array_push( $arr, $res->meta_key );
		    }
		    
		    return $arr;
    	}
    	
    	/**
    	 * get_meta_keys_by_post_types function.
    	 * 
    	 * @access public
    	 * @param array $types
    	 * @return array
    	 */
    	public function get_meta_keys_by_post_types( $types )
    	{
    		$metas = array();
    		
    		if( is_array( $types ) )
    		{
	    		foreach( $types as $type )
	    		{
	    			array_push( $metas, $this->get_postmetakeys_by_post_type( $type ) );
	    		}
	    		$ret = $this->flattenArray($metas);
    		}
    		else
    		{
	    		$ret = array( 'error' => __("Argument should be an array. " .  __METHOD__, 'wpv-views' ) );
    		}
    		return $ret;
    		
    	}
    	
    	/**
    	 * flattenArray function.
    	 * 
    	 * @access private
    	 * @param array $array
    	 * @return array $ret_array
    	 */
    	private function flattenArray( $array ){
			  $ret_array = array();
			  
			  if( is_array($array) )
			  {
				  foreach(new RecursiveIteratorIterator(new RecursiveArrayIterator($array)) as $value)
				  {
			     	$ret_array[] = $value;
			      }
			  }
			  else
			  {
				  $ret_array = array('error' => __('Argument should be an array ' . __METHOD__, 'wpv-views') );
			  }
			  
			  return $ret_array;
		}
		
		/**
		 * getCustomPostTypesJson function.
		 * 
		 * @access public
		 * @param mixed $object_types
		 * @param bool $include_hidden (default: false)
		 * @return array
		 */
		public function getPostTypesMetas( $object_types, $include_hidden = false )
		{
			global $WP_Views;
			
			if( !is_array($object_types) ) return array('error' => __('Parameter should be an array ' . __METHOD__, 'wpv-views' )  );
			
			
			$metaKeysAll = $this->get_meta_keys_by_post_types( $object_types );
			
			if( isset( $metaKeysAll['error'] ) ) return array('error' => $metaKeysAll['error'] );
			
		
			$ret = $metaKeysAll;
			
			if (!$include_hidden) {
				
				$options = $WP_Views->get_options();
				if (isset($options['wpv_show_hidden_fields'])) {
					
					$include_these_hidden = explode(',', $options['wpv_show_hidden_fields']);
				} else {
					
					$include_these_hidden = array();
				}
				// exclude hidden fields (starting with an underscore)
				foreach ($ret as $index => $field) {
					if (strpos($field, '_') === 0) {
						if (!in_array($field, $include_these_hidden)) {
							unset($ret[$index]);
						}
					}
				}
				
				
				
				if ( $ret ) 
				{
                	natcasesort($ret);
                }
			}
		
			//throw away keys if any and make sure we do not have duplicates: we do no trust you.
			return array_values( array_unique( $ret) );
		      
		}
		
    	/**
    	 * getCustomFieldDataType function.
    	 * 
    	 * @access private
    	 * @param mixed $field
    	 * @return string
    	 */
    	private function getCustomFieldDataType( $field )
    	{
    		
	    	if( function_exists('types_get_field_type') )
	    	{
		    	$ret = types_get_field_type($field);
	    	}
	    	else
	    	{
		    	$ret = 'CHAR';
	    	}
	    	return  $ret;
    	}
    	
    	/**
    	 * getCustomFieldsTypes function.
    	 * 
    	 * @access private
    	 * @param array $fields
    	 * @return array
    	 */
    	private function getCustomFieldsDataTypes($fields)
    	{
	    	if( is_array( $fields ) )
	    	{
	    		$ret = array();
	    		
		    	foreach( $fields as $field )
		    	{
			    	 $ret[$field] = $this->getCustomFieldDataType($field);
		    	}
	    	}
	    	else
	    	{
		    	$ret = array('error' => __('Argument should be an array ' . __METHOD__, 'wpv-views') );
	    	}
	    	return $ret;
    	}
    	
    	/**
    	 * formGenericDataStore function.
    	 * 
    	 * @access public
    	 * @param array $data (default: array( ))
    	 * @return mixed json 
    	 */
    	private function formGenericDataStore( $data = array( ) )
    	{
    		if( !is_array($data) ) return json_encode( array( 'error' => __('Parameter should be an array ' . __METHOD__, 'wpv-views' ) ) );
	    	return json_encode( array( 'Data' => $data ) );
    	}
    	
    	/**
    	 * send_data_to_parametric_form function.
    	 * 
    	 * @access public
    	 * @return void
    	 */
    	public function send_data_to_parametric_form()
    	{
    		if( $_POST && wp_verify_nonce( $_POST['wpv_parametric_create_nonce'], 'wpv_parametric_create_nonce' ) )
    		{
    			$metas = $this->getPostTypesMetas( array('post', 'companies', 'employees' ) );
    		//	$types = $this->getCustomFieldsDataTypes( $metas['postmeta'] );
    			
	    		echo $this->formGenericDataStore( 
	    			array(
	    				'metas' => $metas,
	    				'taxes' => $this->getPostTypesTaxonomies( array( 'post', 'companies' ) )
	    				//array('meta_key_types' => $types)
	    				)
	    			);
	    			die();
    		}
    		else
    		{
	    		die( __('Nonce problem: apparently we do not know from where the request comes from.') );
    		}
	    	
    	}
    	
    	
    	/**
    	 * initScripts function.
    	 * 
    	 * @access public
    	 * @return void
    	 */
    	public function initStatics()
    	{
    		$this->parametric_enqueue_scripts();
    	}
    	
    	
    	/**
    	 * parametric_enqueue_scripts function.
    	 * 
    	 * @access public
    	 * @return void
    	 */
    	private function parametric_enqueue_scripts()
    	{
    		wp_enqueue_script('knockout');
    		wp_enqueue_script('jquery-ui-dialog');
    		wp_enqueue_style("wp-jquery-ui-dialog");
    		wp_register_style( 'jquery-style', 'http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.1/themes/smoothness/jquery-ui.css', true); 
    		wp_enqueue_style( 'jquery-style' );
    		wp_enqueue_script( 'wpv-parametric-admin-script');
    		wp_enqueue_style( 'wpv-parametric-admin-style');
    		wp_localize_script( 'wpv-parametric-admin-script', 
    							'Parametric', 
    							array( 
    								'WPV_URL' =>  WPV_URL,
    								'wpv_parametric_create_nonce' => wp_create_nonce( 'wpv_parametric_create_nonce' )
    							) 
    						);
       	}
    	
    	
    	////////// OLD METHODS TO BE REVRITTEN //////////////
			   
        /**
         * Adding a "V" button to the menu
         * @param string $context
         * @param string $text_area
         * @param boolean $standard_v is this a standard V button
         */
        public function add_form_button( $context, $text_area = 'textarea#content',
                $standard_v = true, $add_views = false ) {
            global $wp_version, $wplogger;
            
            if( defined('WPV_LOGGING_STATUS') && WPV_LOGGING_STATUS == 'debug' )
            {
	            list(, $caller) = debug_backtrace(false);			
	            $this->logger->log( sprintf('Adding form buttons %s %s %s %s %s', $context, $text_area, $standard_v ? 'yes' : 'no', $add_views ? 'yes' : 'no', $caller['function'] ) );
            }			
			
			
			
            // WP 3.3 changes ($context arg is actually a editor ID now)
            if ( version_compare( $wp_version, '3.1.4', '>' ) && !empty( $context ) ) {
                $text_area = $context;
            }

            // Apply filters
            $this->items = apply_filters( 'editor_addon_items_' . $this->name,
                    $this->items );

 
            $menus_output = '';

            $addon_button = '<img src="' . $this->media_button_image . '" class="parametric-button-open js-button_' . $this->name .  ' js-parametric-open-window" />';

            
            // generate output content
            $out = '<ul class="editor-parametric-wrapper js-editor_addon_wrapper"><li>' . $addon_button . '</li></ul>';
            
            $out .= '<div id="js-parametric-form-dialog" class="parametric-form-dialog"></div>';

            // WP 3.3 changes
            if ( version_compare( $wp_version, '3.1.4', '>' ) ) {
                echo apply_filters( 'wpv_add_media_buttons', $out );
            } else {
                return apply_filters( 'wpv_add_media_buttons', $context . $out );
            }
        }

          /*

          Add the wpv_views button to the toolbar.

         */

        function wpv_mce_add_button( $buttons )
        {
            array_push( $buttons, "separator",
                    str_replace( '-', '_', $this->name ) );
            return $buttons;
        }

        /*

          Register this plugin as a mce 'addon'
          Tell the mce editor the url of the javascript file.
         */

        function wpv_mce_register( $plugin_array )
        {
            $plugin_array[str_replace( '-', '_', $this->name )] = $this->plugin_js_url;
            return $plugin_array;
        }

    }    

} 

