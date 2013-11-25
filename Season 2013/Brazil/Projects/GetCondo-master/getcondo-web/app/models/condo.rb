class Condo
  include Mongoid::Document

  attr_accessible :name

  field :name, type: String

  has_many :residents, class_name: 'User', inverse_of: :condo
  has_many :news

  def syndic
    residents.where(role: User::SYNDIC_ROLE).first
  end
end
