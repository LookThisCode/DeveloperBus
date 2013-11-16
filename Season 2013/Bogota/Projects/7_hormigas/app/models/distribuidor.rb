class Distribuidor < ActiveRecord::Base
	attr_accessible :nombre, :nit_cedula, :nombre_representante_legal, :logo_o_foto, :descripcion, :motivacion_registro, :calificacion

  has_one    :user
  has_many   :local
  has_many   :keyword
  has_many   :negocio_cerrado
  has_many   :comentario

#   field :nombre, 											type: String
#   field :nit_cedula,      						type: String
#   field :nombre_representante_legal,  type: String
#   field :logo_o_foto,      						type: String
#   field :descripcion,      						type: String
#   field :motivacion_registro,					type: String
#   field :calificacion,								type: String
end
