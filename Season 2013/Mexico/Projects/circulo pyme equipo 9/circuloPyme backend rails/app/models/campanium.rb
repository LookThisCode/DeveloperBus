class Campanium < ActiveRecord::Base
  attr_accessible :id, :titulo,:img_url,:lugares,:keywords,:tienda_1_reference, :tienda_2_reference, :tienda_3_reference, :tienda_4_reference, :tienda_5_reference
end
