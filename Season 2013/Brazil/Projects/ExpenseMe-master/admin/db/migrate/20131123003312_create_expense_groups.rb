class CreateExpenseGroups < ActiveRecord::Migration
  def change
    create_table :expense_groups do |t|
      t.datetime :startdate
      t.datetime :enddate
      t.decimal :budget

      t.timestamps
    end
  end
end
