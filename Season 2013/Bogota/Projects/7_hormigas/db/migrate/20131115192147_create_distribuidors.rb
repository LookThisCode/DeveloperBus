class CreateDistribuidors < ActiveRecord::Migration
  def change
    create_table :distribuidors do |t|
      t.string :nombre
      t.string :nit_cedula
      t.string :nombre_representante_legal
      t.string :logo_o_foto
      t.string :descripcion
      t.string :motivacion_registro
      t.integer :calificacion

      t.timestamps
    end
  end
end
