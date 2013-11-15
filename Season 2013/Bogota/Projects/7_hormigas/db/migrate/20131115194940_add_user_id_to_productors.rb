class AddUserIdToProductors < ActiveRecord::Migration
  def change
  	add_column :productors, :user_id, :integer
  end
end
