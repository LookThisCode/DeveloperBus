class Instance
  include Mongoid::Document
  include Mongoid::Timestamps # adds created_at and updated_at fields

  has_many_and_belongs_to :projects
  belongs_to :stack

end
