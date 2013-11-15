class AddUserIdToDistribuidors < ActiveRecord::Migration
  def change
    add_column :distribuidors, :user_id, :integer
  end
end