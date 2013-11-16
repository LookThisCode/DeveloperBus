class Keyword < ActiveRecord::Base
	attr_accessible :tag

  belongs_to :productor
  belongs_to :distribuidor
end
