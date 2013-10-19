<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

if (!function_exists('curl_init'))
{
	throw new Exception('Curl extension needed! - sudo apt-get install php5-curl');
}

class bsas_iterator implements Iterator {

	protected $_offset = 0;
	protected $_position = 0;
	protected $_limit = 20;
	protected $_items = FALSE;

	protected $bsas_obj;
	protected $name;
	protected $arguments;

	public function __construct($bsas_obj, $name, $arguments)
	{
		$this->bsas_obj = $bsas_obj;
		$this->name = $name;
		$this->arguments = $arguments;

		$arguments['Limit'] = $this->_limit;
	}

	protected function fetch_next_batch()
	{
		$this->arguments['Offset'] = $this->_offset;
		$this->_position = 0;
		$this->_offset += $this->_limit;
		$xml = $this->bsas_obj->get($this->name, $this->arguments);
		$this->_items = $xml->Item;
	}

	function rewind() {
		$this->_offset = 0;
		$this->fetch_next_batch();
	}

	function current() {
		return $this->_items[$this->_position];
	}

	function key() {
		return $this->_offset + $this->_position;
	}

	function next() {
		++$this->_position;
	}

	function valid() {
		if (count($this->_items) <= $this->_position)
		{
			$this->fetch_next_batch();
			return count($this->_items) > 1;
		}

		return TRUE;
	}

}

class bsas_gob_data {

	protected $_base_url = 'http://agendacultural.buenosaires.gob.ar/webservice/response/client.php?Method=Get';

	public function get($name, array $arguments = array())
	{
		$url = $this->_base_url . ucfirst($name) . 'ListFiltered&' . http_build_query($arguments);

		$ch = curl_init($url);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
		$xml = curl_exec($ch);
		return simplexml_load_string($xml);
	}

	public function iter($name, array $arguments = array())
	{
		return new bsas_iterator($this, $name, $arguments);
	}

}

/* End of file bsas_gob_data.php */
/* Location: ./application/libraries/bsas_gob_data.php */
