class CreateCampania < ActiveRecord::Migration
  def change
    create_table :campania do |t|
      t.integer :id
      t.string :titulo

      t.timestamps
    end
  end
end
