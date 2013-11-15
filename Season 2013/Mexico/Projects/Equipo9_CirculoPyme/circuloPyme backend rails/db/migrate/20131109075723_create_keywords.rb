class CreateKeywords < ActiveRecord::Migration
  def change
    create_table :keywords do |t|
      t.integer :id
      t.text :keyword
      t.integer :id_campania

      t.timestamps
    end
  end
end
