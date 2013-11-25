class Api::BaseController < ApplicationController

  def current_condo
    @condo = Condo.where(id: params[:condo_id]).first
  end

  def current_user
    @user = User.where(id: params[:token]).first
  end
end
