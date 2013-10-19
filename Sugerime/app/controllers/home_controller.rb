class HomeController < ApplicationController


  #skip_before_filter :authenticate_user!

  def index
    @json = Address.where("shopping_id is not NULL").to_gmaps4rails do |address, marker|

      marker.infowindow render_to_string(:partial => "/home/infowindow", :locals => { :address => address, :company_count => address.shopping.companies.all.count})
      #marker.picture({
      #                   :picture => "http://www.blankdots.com/img/github-32x32.png",
      #                   :width   => 32,
      #                   :height  => 32
      #               })
      marker.title   address.shopping.name
      marker.json({ :shopping_id => address.shopping.id, :shopping_name => address.shopping.name})

    end
  end
end
