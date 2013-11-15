<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Band_model extends CI_Model {

	public function get_by_id($band_id, $return_media = FALSE)
	{
		$this->db->where('b.band_id', $band_id);
		return $this->get($return_media);
	}

	public function get_by_gid($gid, $return_media = FALSE)
	{
		$this->db->where('b.gid', $gid);
		return $this->get($return_media);
	}

	protected function get($return_media = FALSE)
	{
		$band = $this->db
			->select('m.*, b.*')
			->join('band b', 'm.media_id = b.media_id', 'right')
			->get('media m')->row();

		if ($return_media)
		{
			$band->media = $this->db->where('band_id', $band->band_id)->get('media')->result();
		}

		return $band;
	}

	public function update_by_gid($guser)
	{
		$band = $this->get_by_gid($guser['uid']);
		if ( ! $band)
		{
			$this->db->insert('band', array(
				'gid'         => $guser['uid'],
				'name'        => $guser['name'],
				'city'        => $guser['location'],
				'description' => $guser['description']
			));
			$band = $this->get_by_gid($guser['uid']);
		}

		$this->load->model('media_model');
		$image = $this->media_model->add($band->band_id, 'image', $guser['image']);

		if (isset($image->added))
		{
			$this->db
				->where('band_id', $band->band_id)
				->update('band', array(
					'media_id' => $image->media_id
				));
			$band = $this->get_by_gid($guser['uid']);
		}

		return $band;
	}

	function youtube_service($token)
	{
		$this->load->library('Google/Google_Client');
		$client = new Google_Client();
		$client->setAccessToken($token->to_google_token());

		$this->load->library('Google/contrib/Google_YouTubeService', $client);
		return new Google_YouTubeService($client);
	}

	function fetch_youtube_videos($band, $token)
	{
		$youtube = $this->youtube_service($token);

		$channelsResponse = $youtube->channels->listChannels('contentDetails', array(
			'mine' => 'true'
		));
		foreach ($channelsResponse['items'] as $channel)
		{
			$uploadsListId = $channel['contentDetails']['relatedPlaylists']['uploads'];

			$playlistItemsResponse = $youtube->playlistItems->listPlaylistItems('snippet', array(
				'playlistId' => $uploadsListId,
				'maxResults' => 50
			));

			$ids = array();
			foreach ($playlistItemsResponse['items'] as $playlistItem)
			{
				$ids[] = $playlistItem['snippet']['resourceId']['videoId'];
			}

			$videosResponse = $youtube->videos->listVideos('snippet,status', array(
				'id' => implode(',', $ids)
			));

			foreach ($videosResponse['items'] as $videoItem)
			{
				if ($videoItem['status']['uploadStatus'] == 'processed')
				{
					$this->media_model->add($band->band_id, 'youtube', $videoItem['id'], $videoItem['snippet']['title']);
				}
			}
		}
	}

}

/* End of file band_model.php */
/* Location: ./application/models/band_model.php */
