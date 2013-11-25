class CreateExpenses < ActiveRecord::Migration
  def change
    create_table :expenses do |t|
      t.string :location
      t.decimal :amount
      t.string :description
      t.string :receipt
      t.datetime :date
      t.boolean :billable
      t.boolean :reimbursable

      t.timestamps
    end
  end
end
