class CreateLugares < ActiveRecord::Migration
  def change
    create_table :lugares do |t|
      t.integer :id
      t.text :lugar_nombre
      t.integer :campania_id

      t.timestamps
    end
  end
end
