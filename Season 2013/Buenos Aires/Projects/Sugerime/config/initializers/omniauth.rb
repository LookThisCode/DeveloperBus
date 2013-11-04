Rails.application.config.middleware.use OmniAuth::Builder do
  provider :google_oauth2, "78905282736.apps.googleusercontent.com", "-XFmH-EJhE-OPYi8d_2vxk_e"
end