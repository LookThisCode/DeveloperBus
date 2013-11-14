class Project
  include Mongoid::Document
  include Mongoid::Timestamps # adds created_at and updated_at fields
  include Mongoid::Slug

  field :name, :type => String
  field :description, :type => String
  slug :name

  belongs_to :user
  has_many_and_belongs_to :instances

end
