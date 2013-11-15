Rails.application.config.middleware.use OmniAuth::Builder do
  provider :google_oauth2, ENV["GOOGLE_ACCESS_KEY_ID"], ENV["GOOGLE_SECRET_ACCESS_KEY"]
end