class News
  include Mongoid::Document

  attr_accessible :title, :description, :condo, :condo_id

  field :title, type: String
  field :description, type: String

  belongs_to :condo
end
