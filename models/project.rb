class Project
  include Mongoid::Document
  include Mongoid::Timestamps 
  include Mongoid::Slug

  field :name, :type => String
  field :description, :type => String
  slug :name

  belongs_to :user
  has_and_belongs_to_many :instances

end
