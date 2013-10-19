class Keyword < ActiveRecord::Base
  has_and_belongs_to_many :companies
  has_and_belongs_to_many :users
  attr_accessible :name
end
