class Instance
  include Mongoid::Document
  include Mongoid::Timestamps

  has_and_belongs_to_many :projects
  belongs_to :stack

end
