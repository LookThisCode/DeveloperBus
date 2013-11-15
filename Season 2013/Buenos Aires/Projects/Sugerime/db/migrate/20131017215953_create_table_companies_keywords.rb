class CreateTableCompaniesKeywords < ActiveRecord::Migration
  def change
    create_table :companies_keywords do |t|
      t.integer :keyword_id
      t.integer :company_id
      t.datetime :created_at,:default => DateTime.now
      t.datetime :updated_at,:default => DateTime.now

    end
  end
end
