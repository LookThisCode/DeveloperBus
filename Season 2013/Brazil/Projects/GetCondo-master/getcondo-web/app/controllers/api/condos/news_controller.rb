class Api::Condos::NewsController < Api::BaseController

  def index
    @news = current_condo.news
    render json: { result: @news }
  end

end
