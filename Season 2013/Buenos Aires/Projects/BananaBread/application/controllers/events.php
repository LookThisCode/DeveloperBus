<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Events extends MY_Controller {

	public function index()
	{
		$this->load->model('event_model');

		$tags = $this->session->userdata('preferences');

		$this->load_view('events/events', array(
			'tags' => $tags ? $tags : array(),
			'load_js'=>true
		));
	}

	public function get_band_near() {
		$this->load->model('event_model');
		$tags = $this->input->get('tags');
		$lat = $this->input->get('lat');
		$lng = $this->input->get('lng');
		$events = array();
		if(!$lat || !$lng) {
			$events = $this->event_model->find_any();
		} else {
			$events = $this->event_model->find_near($lat, $lng, 50, $tags);
			if(count($events->result()) == 0) {
				$events = $this->event_model->find_any();
			}
		}
		$ret = array();
		foreach ($events->result() as $event) {
			$ret[] = array(
				'img'=>base_url('assets/images/banda/bandaINDEX.jpg'),
				'url'=>site_url('band/'.$event->band_id),
				'name'=>$event->name,
				'desc'=>substr($event->description, 0, 100),
				'day'=>$event->date
			);
		}
		echo json_encode($ret);
	}

	public function get_band_any() {
		$this->load->model('event_model');
		$events = array();
		$events = $this->event_model->find_any();
		$ret = array();
		foreach ($events->result() as $i=>$event) {
			$ret[] = array(
				'img'=>base_url('assets/images/banda/banda'.($i%10+1).'.jpg'),
				'url'=>site_url('band/'.$event->band_id),
				'name'=>$event->name,
				'desc'=>substr($event->description, 0, 100),
				'day'=>$event->date
			);
		}
		echo json_encode($ret);
	}

}

/* End of file welcome.php */
/* Location: ./application/controllers/welcome.php */
