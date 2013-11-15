class Shopping < ActiveRecord::Base
  
  attr_accessible :name, :address, :address_attributes

  has_many :companies

  has_one :address

  accepts_nested_attributes_for :address

end
