class CreateTableKeywordsUsers < ActiveRecord::Migration
  def change
    create_table :keywords_users do |t|
      t.integer :keyword_id
      t.integer :user_id
      t.datetime :created_at,:default => DateTime.now
      t.datetime :updated_at,:default => DateTime.now

    end
  end
end
