class CreateAddresses < ActiveRecord::Migration
  def change
    create_table :addresses do |t|
      t.integer :country_id
      t.integer :state_id
      t.references :shopping
      t.float :latitude
      t.float :longitude
      t.boolean :gmaps
      t.string :street
      t.string :number
      t.string :city
      t.integer :user_id


      t.timestamps
    end
  end
end
