<?php

/**
* GeoIPCountryWhois Locator
*
* This file locates the remote's country using it's IP, parsed in a CSV values
*
* PHP versions 4 and 5
*
* LICENSE: This source file is subject to version 3.0 of the PHP license
* that is available through the world-wide-web at the following URI:
* http://www.php.net/license/3_0.txt.  If you did not receive a copy of
* the PHP License and are unable to obtain it through the web, please
* send a note to license@php.net so we can mail you a copy immediately.
*
* @category   Localization
* @package    geoip
* @author     Marius Zadara <marius_victor@yahoo.com>
* @copyright  (C) Marius Zadara <marius_victor@yahoo.com>
* @license    http://www.php.net/license/3_0.txt  PHP License 3.0
* @version    1.0
* @since      File available since Release 1.0
*/



class GeoIP
{
	var $_path;


	/**
	* Boolean found
	*
	* @since 1.0
	* @var boolean
	*/
	var $_found			= false;

	/**
	* Starting address of the class
	*
	* @return void
	* @var boolean
	*/
	var $_start_ip 		= false;

	/**
	* Ending address of the class
	*
	* @return void
	* @var boolean
	*/
	var $_end_ip 		= false;

	/**
	* Country code
	*
	* @return void
	* @var boolean
	*/
	var $_country_code 	= false;

	/**
	* Country name
	*
	* @return void
	* @var boolean
	*/
	var $_country_name 	= false;

	/**
	* CSV values
	*
	* @return void
	* @var boolean
	*/
	var $_data			= array();

	/**
	* Long representation of the IP
	*
	* @return void
	* @var boolean
	*/
	var $_longip		= 0;

	/**
	* Class constructor
	*
	* @return void
	* @access public
	* @static
	* @since 1.0
	*/
	function GeoIP()
	{
		$filename = "GeoIPCountryWhois.gz";
		$this->_data = gzfile($filename);
	}

	/**
	* IP address conversion: A.B.C.D -> log
	*
	* @param string $string IP Address: A.B.C.D
	* @return long
	* @access private
	* @static
	* @since 1.0
	*/
	private function convert2number($string)
	{
		$pattern= "\"([0-9]+)\"";
		if (ereg($pattern, $string, $regs))
			return (int)$regs[1];
	}

	/**
	* IP searching
	*
	* @param string $beg Starting index
	* @param string $end Ending index
	* @return long/bool
	* @access private
	* @static
	* @since 1.0
	*/
	private function search($beg, $end)
	{
		/**
		This is the main search functions.

		It uses the DIVIDE-ET-IMPERA paradigm to find the specified IP in the data array.
		Each data in the array looks like:
			"IP_1","IP_2","LONG_IP_1","LONG_IP_2","COUNTRY_CODE","COUNTRY_NAME"

		IP is belonging to COUNTRY_NAME if complies with the formula:
			LONG_IP_1 <= LONG_IP <= LONG_IP_2

		The function returns either:
			- the pozition in the array IF FOUND
			- false, IF NOT FOUND
		*/

		$mid = ceil(($end + $beg) / 2);


		$arr_beg = array();
		$arr_mid = array();
		$arr_end = array();

		$arr_beg = explode(",", $this->_data[$beg]);
		$arr_mid = explode(",", $this->_data[$mid]);
		$arr_end = explode(",", $this->_data[$end]);


		$arr_beg[2] = str_replace('"', '', $arr_beg[2]);
		$arr_beg[3] = str_replace('"', '', $arr_beg[3]);

		$arr_mid[2] = str_replace('"', '', $arr_mid[2]);
		$arr_mid[3] = str_replace('"', '', $arr_mid[3]);

		$arr_end[2] = str_replace('"', '', $arr_end[2]);
		$arr_end[3] = str_replace('"', '', $arr_end[3]);




		if (($this->_longip >= $arr_beg[2]) && ($this->_longip <= $arr_beg[3]))
		{
			unset($arr_beg); unset($arr_mid); unset($arr_end);
			return $beg;
		}
		else
			if (($this->_longip >= $arr_mid[2]) && ($this->_longip <= $arr_mid[3]))
			{
				unset($arr_beg); unset($arr_mid); unset($arr_end);
				return $mid;
			}
			else
				if (($this->_longip >= $arr_end[2]) && ($this->_longip <= $arr_end[3]))
				{
					unset($arr_beg); unset($arr_mid); unset($arr_end);
					return $end;

				}
				else
					if (($this->_longip > $arr_beg[3]) && ($this->_longip < $arr_mid[2]))
					{
						unset($arr_beg); unset($arr_mid); unset($arr_end);
						return $this->search($beg, $mid);
					}
					else
						if (($this->_longip > $arr_mid[3]) && ($this->_longip < $arr_end[2]))
						{
							unset($arr_beg); unset($arr_mid); unset($arr_end);
							return $this->search($mid, $end);
						}
						else
						{
							unset($arr_beg); unset($arr_mid); unset($arr_end);
							return false;
						}
	}


	/**
	* IP conversion
	*
	* @param string $address IP address in the A.B.C.D format
	* @return long
	* @access private
	* @static
	* @since 1.0
	*/
	private function IpAddress2IpNumber($address)
	{
		$pattern = "([0-9]+).([0-9]+).([0-9]+).([0-9]+)";

		if (ereg($pattern, $address, $regs))
			return $number = $regs[1] * 256 * 256 * 256 + $regs[2] * 256 * 256 + $regs[3] * 256 + $regs[4];
		else
			return false;
	}


	/**
	* MAIN SEARCH
	*
	* @param string $ip The IP Searched
	* @return void
	* @access private
	* @static
	* @since 1.0
	*/
	public function search_ip($ip)
	{
		// if not localhost ...
		if (($ip != "127.0.0.1") && ($ip != "0.0.0.1"))
		{
			$this->_longip = $this->IpAddress2IpNumber($ip);

			if ($this->_longip !== false)
				$poz = $this->search(0, sizeof($this->_data)-1);

			if ($poz !== false)
			{
				$info = explode(",", $this->_data[$poz]);

				$this->_found			= true;
				$this->_start_ip 		= str_replace('"', '', $info[0]);
				$this->_end_ip 			= str_replace('"', '', $info[1]);
				$this->_country_code 	= str_replace('"', '', $info[4]);
				$this->_country_name 	= str_replace('"', '', $info[5]);
			}
		}
	}

	/**
	* IP Class start
	*
	* @return string
	* @access public
	* @static
	* @since 1.0
	*/
	public function getStartIp()		{return $this->_start_ip;}

	/**
	* IP Class end
	*
	* @return string
	* @access public
	* @static
	* @since 1.0
	*/
	public function getEndIp()			{return $this->_end_ip;}

	/**
	* IP Country Code
	*
	* @return string
	* @access public
	* @static
	* @since 1.0
	*/

	public function getCountryCode()	{return $this->_country_code;}

	/**
	* IP Country name
	*
	* @return string
	* @access public
	* @static
	* @since 1.0
	*/
	public function getCountryName()	{return $this->_country_name;}

	/**
	* IP Found flag
	*
	* @return boolean
	* @access public
	* @static
	* @since 1.0
	*/
	public function found()				{return $this->_found;}


	/**
	* Class unset
	*
	* @return void
	* @access public
	* @static
	* @since 1.0
	*/
	public function destroy()
	{
		unset($this->_found);
		unset($this->_start_ip);
		unset($this->_end_ip);
		unset($this->_country_code);
		unset($this->_country_name);
		unset($this->_data);
		unset($this);
	}
}


?>


