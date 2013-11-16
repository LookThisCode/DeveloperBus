class Comentario < ActiveRecord::Base
	attr_accessible :comentario

  belongs_to :distribuidor
  belongs_to :productor
end
