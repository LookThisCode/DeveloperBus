class ExpenseGroup < ActiveRecord::Base
  has_many :expenses
  belongs_to :project

  def to_s
    "Expense group ##{id}"
  end
end
