class Keys
  include Mongoid::Document
  include Mongoid::Timestamps

  field :name, :type => String
  field :value, :type => String

  belongs_to :user
end
