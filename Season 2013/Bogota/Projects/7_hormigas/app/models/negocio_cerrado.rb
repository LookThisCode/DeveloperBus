class NegocioCerrado < ActiveRecord::Base
	attr_accessible :acuerdo, :condiciones, :calificacion_transaccion_distribuidor, :calificacion_transaccion_productor, :producto_id

  belongs_to :distribuidor
  belongs_to :producto
end
