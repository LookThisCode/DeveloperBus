<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Migration_create_band extends CI_Migration {

	public function up()
	{
		$this->dbforge->add_field(array(
			'band_id' => array(
				'type'           => 'INT',
				'constraint'     => 11,
				'unsigned'       => TRUE,
				'auto_increment' => TRUE
			),
			'media_id' => array(
				'type'       => 'INT',
				'constraint' => 11,
				'unsigned'   => TRUE
			),
			'name' => array(
				'type'       => 'VARCHAR',
				'constraint' => 255
			),
			'type' => array(
				'type'       => 'VARCHAR',
				'constraint' => 255
			),
			'description' => array(
				'type' => 'TEXT',
				'null' => TRUE
			),
			'tags' => array(
				'type' => 'TEXT',
				'null' => TRUE
			),
			'city' => array(
				'type' => 'VARCHAR',
				'constraint' => 255,
				'null' => TRUE
			),
			'gid' => array(
				'type' => 'VARCHAR',
				'constraint' => 30,
				'null' => TRUE
			)
		));
		$this->dbforge->add_key('band_id');

		$this->dbforge->create_table('band');
	}

	public function down()
	{
		$this->dbforge->drop_table('band');
	}

}

/* End of file 002_create_band.php */
/* Location: ./application/migrations/002_create_band.php */
