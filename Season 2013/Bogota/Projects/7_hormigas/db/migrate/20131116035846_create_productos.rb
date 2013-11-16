class CreateProductos < ActiveRecord::Migration
  def change
    create_table :productos do |t|
      t.string :nombre
      t.string :descripcion
      t.string :url_imagen
      t.references :productor, index: true

      t.timestamps
    end
  end
end
