class Api::SessionsController < Api::BaseController

  def create
    @user = User.where(email: params[:email]).first
    user_data = {}
    if @user
      if @user.valid_password? params[:password]
        user_data = { token: @user.id.to_s, condo_id: @user.condo.id.to_s, name: @user.name, email: @user.email, apartment: @user.apartment }
      end
    end
    if user_data.present?
      render json: user_data
    else
      head 401
    end
  end
end
