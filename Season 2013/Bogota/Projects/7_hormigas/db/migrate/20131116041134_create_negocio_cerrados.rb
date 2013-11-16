class CreateNegocioCerrados < ActiveRecord::Migration
  def change
    create_table :negocio_cerrados do |t|
      t.string :acuerdo
      t.string :condiciones
      t.string :calificacion_transaccion_distribuidor
      t.string :calificacion_transaccion_productor
      t.references :distribuidor, index: true
      t.references :producto, index: true

      t.timestamps
    end
  end
end
