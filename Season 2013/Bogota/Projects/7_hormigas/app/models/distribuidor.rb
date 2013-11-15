class Distribuidor < ActiveRecord::Base
  has_one    :user
  has_many   :imagen_local
  has_many   :keyword
  has_many   :negociacion
  has_and_belongs_to_many :producto
end
