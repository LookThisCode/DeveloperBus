class CreateNegociacions < ActiveRecord::Migration
  def change
    create_table :negociacions do |t|
      t.string :acuerdo
      t.references :distribuidor, index: true
      t.references :productor, index: true

      t.timestamps
    end
  end
end
