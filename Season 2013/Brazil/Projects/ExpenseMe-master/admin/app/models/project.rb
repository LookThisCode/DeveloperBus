class Project < ActiveRecord::Base
  has_many :expense_groups
end
