class CreateUsers < ActiveRecord::Migration
  def change
    create_table :users do |t|
      t.string :name
      t.string :provider
      t.string :uid
      t.text :email
      t.string :encripted_password
      t.string :salt

      t.timestamps
    end
  end
end
