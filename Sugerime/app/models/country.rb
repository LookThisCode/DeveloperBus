class Country < ActiveRecord::Base
  has_many :states

  attr_accessible :name
end
