class Inscription < ActiveRecord::Base
  belongs_to :company
  belongs_to :user
  belongs_to :offer

  attr_accessible :company_id, :user_id, :validation_code
end
