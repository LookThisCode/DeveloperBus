class Productor < ActiveRecord::Base
	has_one    :user
	has_many   :keyword
	has_many   :negociacion
end
