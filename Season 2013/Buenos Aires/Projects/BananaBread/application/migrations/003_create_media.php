<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Migration_create_media extends CI_Migration {

	public function up()
	{
		$this->dbforge->add_field(array(
			'media_id' => array(
				'type'           => 'INT',
				'constraint'     => 11,
				'unsigned'       => TRUE,
				'auto_increment' => TRUE
			),
			'band_id' => array(
				'type'       => 'INT',
				'constraint' => 11,
				'unsigned'   => TRUE
			),
			'name' => array(
				'type'       => 'VARCHAR',
				'constraint' => 255,
				'null'       => TRUE
			),
			'type' => array(
				'type'       => 'VARCHAR',
				'constraint' => 255
			),
			'link' => array(
				'type' => 'TEXT'
			)
		));
		$this->dbforge->add_key('media_id');

		$this->dbforge->create_table('media');
	}

	public function down()
	{
		$this->dbforge->drop_table('media');
	}

}

/* End of file 003_create_media.php */
/* Location: ./application/migrations/003_create_media.php */
