<?php

class Media_model extends CI_Model {

	public function add($band_id, $type, $link, $name = NULL)
	{
		$exists = $this->get($band_id, $type, $link);
		if ( ! $exists)
		{
			$this->db->insert('media', array(
				'band_id' => $band_id,
				'type'    => $type,
				'link'    => $link,
				'name'    => $name
			));

			$exists = $this->get($band_id, $type, $link);
			$exists->added = TRUE;
		}

		return $exists;
	}

	public function get($band_id, $type, $link)
	{
		return $this->db
			->where('band_id', $band_id)
			->where('type', $type)
			->where('link', $link)
			->get('media')->row();
	}

}
