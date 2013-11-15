class Address < ActiveRecord::Base
  attr_accessible :city, :country_id, :gmaps, :latitude, :longitude, :number, :state_id, :street, :user_id, :user

  belongs_to :shopping
  belongs_to :user

  acts_as_gmappable

  geocoded_by :full_street_address   # can also be an IP address
  reverse_geocoded_by :latitude, :longitude

  def gmaps4rails_address

  	"#{self.street} #{self.number}, #{self.city}, #{self.state}, #{self.country}"

  end

  def state
    if self.state_id.nil?
      ""
    else
      State.find(self.state_id).name
    end

  end

   def country
     if self.country_id.nil?
       ""
     else
       Country.find(self.country_id).name
     end

  end 
end
