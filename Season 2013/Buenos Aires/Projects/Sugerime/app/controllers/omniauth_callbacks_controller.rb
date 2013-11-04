class OmniauthCallbacksController < Devise::OmniauthCallbacksController
  def google_oauth2
    user = User.from_omniauth(request.env["omniauth.auth"])
    if user.persisted?
      flash.notice = "Autenticado a través de Google"
      sign_in_and_redirect user
    else
      session["devise.user_attributes"] = user.attributes
      flash.notice = "Ya casi terminas. Por favor indicá una contraseña para terminar de configurar tu cuenta"
      redirect_to new_user_registration_url
    end
  end
end