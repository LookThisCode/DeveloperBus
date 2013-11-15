class Distribuidor < ActiveRecord::Base
  has_one    :user
  has_many   :imagen_local
  has_many   :keyword
  has_many   :negociacion
  has_and_belongs_to_many :producto

#   field :nombre, 											type: String
#   field :nit_cedula,      						type: String
#   field :nombre_representante_legal,  type: String
#   field :logo_o_foto,      						type: String
#   field :descripcion,      						type: String
#   field :motivacion_registro,					type: String
#   field :calificacion,								type: String
end
