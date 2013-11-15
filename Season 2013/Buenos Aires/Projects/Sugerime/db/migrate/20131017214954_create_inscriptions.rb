class CreateInscriptions < ActiveRecord::Migration
  def change
    create_table :inscriptions do |t|
      t.integer :user_id
      t.integer :company_id
      t.integer :offer_id
      t.string :validation_code

      t.timestamps
    end
  end
end
