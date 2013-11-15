class User < ActiveRecord::Base
  has_one    :distribuidor
  has_one    :productor
end
