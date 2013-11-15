class EditFieldsForTable < ActiveRecord::Migration
  def change
  	add_column :campania, :img_url, :text
  	add_column :campania, :lugares, :text
  	add_column :campania, :keywords, :text
  end
end
