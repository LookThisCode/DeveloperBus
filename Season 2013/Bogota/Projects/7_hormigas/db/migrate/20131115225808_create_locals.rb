class CreateLocals < ActiveRecord::Migration
  def change
    create_table :locals do |t|
      t.string :ciudad
      t.string :pais
      t.float :lat
      t.float :lon
      t.string :comentario
      t.references :distribuidor, index: true

      t.timestamps
    end
  end
end
