class Offer < ActiveRecord::Base
  belongs_to :company
  belongs_to :user
  has_many :inscriptions

  attr_accessible :company_id, :description, :expiration, :user, :user_id
end
