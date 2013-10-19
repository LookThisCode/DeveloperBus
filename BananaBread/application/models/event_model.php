<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Event_model extends CI_Model {

	/**
	 * Distance in kilometers
	 * @TODO define order by
	 * @robado https://developers.google.com/maps/articles/phpsqlsearch_v3?csw=1
	 */
	public function find_near($lat, $lng, $distance = 50, $band_type = FALSE, $limit = 5, $offset = 0)
	{
		$lat = (double) $lat;
		$lng = (double) $lng;
		$this->db
			->join('band b', 'b.band_id = e.band_id')
			->select('b.*, e.*') // Fields with the same name will be overwritten by event.
			->select("6371*acos(cos(radians($lat))*cos(radians(lat))*cos(radians(lng)-radians($lng))+sin(radians($lat))*sin(radians(lat))) AS distance")
			->having('distance <', $distance)
			->limit($limit, $offset);

		if ($band_type)
		{
			$this->db->where('b.type', $band_type);
		}

		return $this->db->get('event e');
	}

	public function find_any($band_type = FALSE, $limit = 5, $offset = 0)
	{
		$this->db
			->join('band b', 'b.band_id = e.band_id')
			->select('b.*, e.*') // Fields with the same name will be overwritten by event.
			->limit($limit, $offset);

		if ($band_type)
		{
			$this->db->where('b.type', $band_type);
		}

		return $this->db->get('event e');
	}

}

/* End of file event_model.php */
/* Location: ./application/models/event_model.php */
