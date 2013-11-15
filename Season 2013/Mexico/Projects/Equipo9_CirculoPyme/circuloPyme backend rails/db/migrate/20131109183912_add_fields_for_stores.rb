class AddFieldsForStores < ActiveRecord::Migration
  def change
  	add_column :campania, :tienda_1_reference, :text
  	add_column :campania, :tienda_2_reference, :text
  	add_column :campania, :tienda_3_reference, :text
  	add_column :campania, :tienda_4_reference, :text
  	add_column :campania, :tienda_5_reference, :text
  end
end
