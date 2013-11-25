class Relationships < ActiveRecord::Migration
  def change
    add_column :expenses, :expense_category_id, :integer
    add_column :expenses, :expense_group_id, :integer
    add_column :expense_groups, :project_id, :integer
    add_column :expenses, :user_id, :integer
  end
end
