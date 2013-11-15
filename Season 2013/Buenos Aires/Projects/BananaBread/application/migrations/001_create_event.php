<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Migration_create_event extends CI_Migration {

	public function up()
	{
		$this->dbforge->add_field(array(
			'event_id' => array(
				'type'           => 'INT',
				'constraint'     => 11,
				'unsigned'       => TRUE,
				'auto_increment' => TRUE
			),
			'gcba_id' => array(
				'type'       => 'INT',
				'constraint' => 11,
				'unsigned'   => TRUE
			),
			'band_id' => array(
				'type'       => 'INT',
				'constraint' => 11,
				'unsigned'   => TRUE
			),
			'description' => array(
				'type' => 'TEXT'
			),
			'date' => array(
				'type' => 'TIMESTAMP'
			),
			'lat' => array('type' => 'double'),
			'lng' => array('type' => 'double'),
			'city' => array(
				'type'       => 'VARCHAR',
				'constraint' => 255
			)
		));
		$this->dbforge->add_key('event_id');

		$this->dbforge->create_table('event');
	}

	public function down()
	{
		$this->dbforge->drop_table('event');
	}

}

/* End of file 001_create_event.php */
/* Location: ./application/migrations/002_create_event.php */
