class User
  include Mongoid::Document
  include Mongoid::Timestamps # adds created_at and updated_at fields

  field :email, :type => String
  field :password, :type => String

  has_many :projects

end
