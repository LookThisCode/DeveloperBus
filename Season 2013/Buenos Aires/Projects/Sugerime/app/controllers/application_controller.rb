class ApplicationController < ActionController::Base

  before_filter :authenticate_user!


  protect_from_forgery

  def after_sign_in_path_for(resource)

    if current_user.user_role_id == 1 or current_user.user_role_id == nil
        root_path
    else
      users_path
    end

  end

  def after_sign_up_path_for(resource)

    if current_user.user_role_id == 1 or current_user.user_role_id == nil
      root_path
    else
      users_path
    end

  end

end
