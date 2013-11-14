module Klou
  class App < Padrino::Application
    register Padrino::Rendering
    register Padrino::Mailer
    register Padrino::Helpers
    register CompassInitializer
    register PasswordHasher

    include PasswordHasher
    enable :sessions

    
  end
end
