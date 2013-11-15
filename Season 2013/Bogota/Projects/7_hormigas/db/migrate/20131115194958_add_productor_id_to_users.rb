class AddProductorIdToUsers < ActiveRecord::Migration
  def change
  	add_column :users, :productor_id, :integer
  end
end
