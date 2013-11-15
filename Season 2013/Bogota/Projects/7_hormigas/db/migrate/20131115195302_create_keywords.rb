class CreateKeywords < ActiveRecord::Migration
  def change
    create_table :keywords do |t|
      t.string :keyword
      t.string :descripcion
      t.references :productor, index: true
      t.references :distribuidor, index: true

      t.timestamps
    end
  end
end
