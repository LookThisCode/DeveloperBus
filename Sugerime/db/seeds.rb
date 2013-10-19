# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)


UserRole.delete_all
UserRole.create!(:name => "Cliente")
UserRole.create!(:name => "Empresa")

Genre.delete_all
Genre.create!(:name => "Masculino")
Genre.create!(:name => "Femenino")

Country.delete_all
Country.create!(:name => "Argentina")

State.delete_all
State.create!(:name => "CABA", :country_id => 1)
State.create!(:name => "Pilar", :country_id => 1)

Keyword.delete_all
Keyword.create!(:name => "Indumentaria Masculina")
Keyword.create!(:name => "Indumentaria Femenina")
Keyword.create!(:name => "Indumentaria para chicos")
Keyword.create!(:name => "Blancos")
Keyword.create!(:name => "Hogar")


