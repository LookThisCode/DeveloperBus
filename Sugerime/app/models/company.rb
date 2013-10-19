class Company < ActiveRecord::Base
  has_and_belongs_to_many :keywords
  belongs_to :user
  belongs_to :shopping
  has_many :offers
  has_many :inscriptions

  attr_accessible :name, :user_id, :shopping_id, :shopping, :keyword_ids

end
