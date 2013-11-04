<?php 
class Youtube_data {

	function youtube_service($token)
	{
		$ci = get_instance();
		$ci->load->library('Google/Google_Client');
		$client = new Google_Client();
		$client->setAccessToken($token->to_google_token());

		$ci->load->library('Google/contrib/Google_YouTubeService', $client);
		return new Google_YouTubeService($client);
	}

	function fetch_youtube_activities($user, $token)
	{
		$youtube = $this->youtube_service($token);
		$response = $youtube->activities->listActivities('id,snippet,contentDetails', array(
			'home'=>true,
			'maxResults'=>20
		));

		$tags = array();
		foreach ($response['items'] as $video)
		{
			if($video['snippet']['type'] == 'recommendation') {
				$channel = $youtube->playlists->listPlaylists('snippet', array(
					'channelId'=>$video['snippet']['channelId']
				));
				foreach($channel['items'] as $item) {
					if(isset($item['snippet']['tags'])) {
						$tags = array_merge($tags, $item['snippet']['tags']);
					}
				}
			}
		}
		return $tags;
	}
}

