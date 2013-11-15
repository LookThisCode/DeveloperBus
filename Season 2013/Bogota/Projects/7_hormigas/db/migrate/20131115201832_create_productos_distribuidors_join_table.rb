class CreateProductosDistribuidorsJoinTable < ActiveRecord::Migration
  def self.up
    create_table :productos_distribuidors, :id => false do |t|
      t.integer :producto_id
      t.integer :distribuidor_id
    end

    add_index :productos_distribuidors, [:producto_id, :distribuidor_id]
  end

  def self.down
    drop_table :productos_distribuidors
  end
end