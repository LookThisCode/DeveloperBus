class CreateImagenLocals < ActiveRecord::Migration
  def change
    create_table :imagen_locals do |t|
      t.string :url
      t.string :descripcion
      t.references :distribuidor, index: true

      t.timestamps
    end
  end
end
