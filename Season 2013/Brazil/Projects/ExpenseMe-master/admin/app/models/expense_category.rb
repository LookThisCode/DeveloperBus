class ExpenseCategory < ActiveRecord::Base
  has_many :expenses
end
