class CreateKeywords < ActiveRecord::Migration
  def change
    create_table :keywords do |t|
      t.string :tag
      t.string :descripcion
      t.references :productor, index: true
      t.references :distribuidor, index: true

      t.timestamps
    end
  end
end
