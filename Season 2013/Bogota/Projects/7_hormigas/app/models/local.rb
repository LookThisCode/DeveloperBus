class Local < ActiveRecord::Base
	attr_accessible :ciudad, :pais, :lat, :lon, :comentario

  belongs_to :distribuidor
  has_many   :imagen_local
end
