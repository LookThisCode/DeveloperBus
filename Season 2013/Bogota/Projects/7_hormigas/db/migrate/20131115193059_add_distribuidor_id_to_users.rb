class AddDistribuidorIdToUsers < ActiveRecord::Migration
  def change
  	add_column :users, :distribuidor_id, :integer
  end
end
