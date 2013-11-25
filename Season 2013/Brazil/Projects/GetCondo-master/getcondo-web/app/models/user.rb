class User
  include Mongoid::Document
  RESIDENT_ROLE = 'resident'
  SYNDIC_ROLE = 'syndic'
  ADMIN_ROLE = 'admin'

  devise :database_authenticatable, :registerable

  attr_accessible :email, :password, :password_confirmation, :name, :role, :condo, :condo_id, :apartment
  belongs_to :condo

  has_many :issues
  has_many :reservations

  field :name,               :type => String
  field :role,               :type => String, :default => 'resident'
  field :apartment,          :type => String
  field :email,              :type => String, :default => ""
  field :encrypted_password, :type => String, :default => ""

end
