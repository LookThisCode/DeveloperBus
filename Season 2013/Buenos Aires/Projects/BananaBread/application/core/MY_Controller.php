<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class MY_Controller extends CI_Controller {

	public function load_view($view, $data = array())
	{
		$this->load->view('layout', array(
			'tpl_view' => $view,
			'tpl_data' => $data,
			'is_logged' => $this->auth_model->is_logged()
		));
	}

}

/* End of file MY_Controller.php */
/* Location: ./application/core/MY_Controller.php */
