if Rails.env.production?
  CarrierWave.configure do |config|
    config.cache_dir = "#{Rails.root}/tmp/"
    config.storage = :fog
    config.fog_credentials = {
      :provider               => 'Google',
      :google_storage_access_key_id      => "#{ENV['GOOGLE_ACCESS_KEY_ID']}",
      :google_storage_secret_access_key  => "#{ENV['GOOGLE_SECRET_ACCESS_KEY']}"
    }
    config.fog_directory  = "#{ENV['BESTPLACES_GOOGLE_BUCKET']}"
    config.asset_host       = "https://#{ENV['BESTPLACES_GOOGLE_BUCKET']}.googlecloud.com"            # optional, defaults to nil
    config.fog_public     = true                                   # optional, defaults to true
    config.fog_attributes = {'Cache-Control'=>'max-age=315576000'}
  end
elsif Rails.env.development?
  CarrierWave.configure do |config|
    config.cache_dir = "#{Rails.root}/tmp/"
    config.storage = :fog
    config.fog_credentials = {
      :provider               => 'Google',       # required
      :google_storage_access_key_id      => "#{ENV['GOOGLE_ACCESS_KEY_ID']}",       # required
      :google_storage_secret_access_key  => "#{ENV['GOOGLE_SECRET_ACCESS_KEY']}"
    }
    config.fog_directory  = "#{ENV['BESTPLACES_GOOGLE_BUCKET']}"                     # required
    config.asset_host       = "https://#{ENV['BESTPLACES_GOOGLE_BUCKET']}.googlecloud.com"            # optional, defaults to nil
    config.fog_public     = true                                   # optional, defaults to true
    config.fog_attributes = {'Cache-Control'=>'max-age=315576000'}
  end
elsif Rails.env.test?
  CarrierWave.configure do |config|
    config.storage = :file
    config.enable_processing = false
  end
end