class Producto < ActiveRecord::Base
	attr_accessible :nombre, :descripcion, :url_imagen

  belongs_to :productor
  has_many   :negocio_cerrado
end
