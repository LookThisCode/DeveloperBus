class ImagenLocal < ActiveRecord::Base
	attr_accessible :url

  belongs_to :local
end
