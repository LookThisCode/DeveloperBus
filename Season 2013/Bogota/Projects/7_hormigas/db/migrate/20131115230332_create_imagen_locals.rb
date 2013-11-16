class CreateImagenLocals < ActiveRecord::Migration
  def change
    create_table :imagen_locals do |t|
      t.string :url
      t.references :local, index: true

      t.timestamps
    end
  end
end
