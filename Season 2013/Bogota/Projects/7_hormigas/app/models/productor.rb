class Productor < ActiveRecord::Base
	attr_accessible :nombre, :nit_cedula, :logo_o_foto, :descripcion, :motivacion_registro, :calificacion

	has_one    :user
	has_many   :keyword
	has_many   :producto
end
