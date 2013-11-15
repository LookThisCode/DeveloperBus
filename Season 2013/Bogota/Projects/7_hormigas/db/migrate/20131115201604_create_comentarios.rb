class CreateComentarios < ActiveRecord::Migration
  def change
    create_table :comentarios do |t|
      t.string :comentario
      t.references :distribuidor, index: true
      t.references :productor, index: true

      t.timestamps
    end
  end
end
