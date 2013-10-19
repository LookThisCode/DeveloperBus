<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Band extends MY_Controller {

	public function show($id)
	{
		$this->load->model('band_model');

		$band = $this->band_model->get_by_id($id);

		if ( ! $band)
			show_404();

		$this->load_view('events/show', array(
			'band' => $this->band_model->get_by_id($id, true),
		));
	}

	public function fetch_from_soundcloud()
	{
		$band = $this->session->userdata('band');
		if ($band)
		{
			list($token, $tracks) = $this->auth_model->login('soundcloud');
			$this->load->model('media_model');
			foreach ($tracks as $track)
			{
				$this->media_model->add($band->band_id, 'soundcloud', $track->id, $track->title);
			}
			redirect('band/' . $band->band_id);
		}

		show_404();
	}

	public function upload($service)
	{
		if (isset($_FILES['file']))
		{
			$tmp_name = tempnam('/tmp/', 'vfu');
			if (move_uploaded_file($_FILES['file']['tmp_name'], $tmp_name))
			{
				$this->session->set_userdata('file_upload', array($tmp_name, $_FILES['file']['type']));
			}
		}

		$this->load->model('auth_model');
		list($token, $user) = $this->auth_model->login($service);
		$band = $this->session->userdata('band');

		$file_upload = $this->session->userdata('file_upload');
		if ($file_upload)
		{
			$this->load->model('media_model');

			if ($service == 'google')
				$this->media_model->add_youtube_upload($band, $token, $file_upload);
			if ($service == 'soundcloud')
				$this->media_model->add_soundcloud_upload($band, $token, $file_upload);

			$this->session->set_userdata('file_upload', FALSE);
		}
		redirect('band/' . $band->band_id);
	}

}
