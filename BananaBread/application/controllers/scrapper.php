<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Scrapper extends CI_Controller {

	public function __construct()
	{
		parent::__construct();

		$this->load->library('migration');
		if ( ! $this->migration->current())
		{
			show_error($this->migration->error_string());
		}
	}

	protected function get_band($youtube)
	{
		return $this->db->where('gid', (string) $youtube)
						->get('band')->row();
	}

	public function gcba()
	{
		echo '<pre>';
		$i = 30;
		$this->load->library('bsas_gob_data');
		foreach ($this->bsas_gob_data->iter('eventos') as $event)
		{
			$categorias = explode(',', $event->IdCategorias);
			if (in_array(76, $categorias) && strlen($event->Youtube) > 0)
			{
				foreach ($event as $key => $val)
				{
					echo $key . ':::' . $val . "\n";
				}

				$band = $this->get_band($event->Youtube);
				if ($band)
				{
					$band_id = $band->band_id;
				}
				else
				{
					$this->db->insert('band', array(
						'name'        => (string) $event->Titulo,
						'type'        => 'Musica',
						'description' => (string) $event->Resumen,
						'city'        => 'Buenos Aires, Argentina',
						'gid'     => (string) $event->Youtube
					));
					$band_id = $this->db->insert_id();
				}

				$hora    = str_pad($event->Hora,    2, "0", STR_PAD_LEFT);
				$minutos = str_pad($event->Minutos, 2, "0", STR_PAD_LEFT);

				$this->db->insert('event', array(
					'band_id'     => $band_id,
					'description' => (string) $event->Descripcion,
					'city'        => 'Buenos Aires, Argentina',
					'date'        => (string) ($event->FechaInicio . ' ' . $hora . ':' . $minutos . ':00')
				));

				echo "\n --- \n\n";
				if (!$i--)
				exit();
			}
		}
		echo '</pre>';
	}

}

/* End of file scrapper.php */
/* Location: ./application/controllers/scrapper.php */
