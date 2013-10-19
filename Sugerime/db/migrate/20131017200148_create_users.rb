class CreateUsers < ActiveRecord::Migration
  def change
    create_table :users do |t|
      t.string :name
      t.integer :user_role_id
      t.string :last_name
      t.date :birthdate
      t.integer :genre_id
      t.string :provider
      t.string :uid
      t.string :avatar
      t.string :need

      t.timestamps
    end
  end
end
