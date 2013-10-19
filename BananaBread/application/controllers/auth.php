<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Auth extends MY_Controller {

	public function login($provider)
	{
		list($token, $user) = $this->auth_model->login($provider);
		if ($user)
		{
			$this->load->model('band_model');
			$band = $this->band_model->update_by_gid($user);
			$this->band_model->fetch_youtube_videos($band, $token);
			$this->session->set_userdata('band', $band);
			$this->session->set_userdata('user', FALSE);
			redirect('band/' . $band->band_id);
		}
	}

	public function logout()
	{
		$this->session->set_userdata('band', FALSE);
		$this->session->set_userdata('user', FALSE);
		$this->session->set_userdata('preferences', FALSE);
		redirect('events');
	}

	public function login_user($provider)
	{
		$this->load->model('auth_model');
		$this->load->model('band_model');
		$this->load->library('youtube_data');
		list($token, $user) = $this->auth_model->login($provider);
		if ($user)
		{
			$this->session->set_userdata('band', FALSE);
			$this->session->set_userdata('user', $user);
			$tags = $this->youtube_data->fetch_youtube_activities($user, $token);
			$this->session->set_userdata('preferences', $tags);
			redirect('events');
		}
	}

}