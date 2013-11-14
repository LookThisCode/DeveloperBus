Klou::App.controllers :base, :map => '/' do
  
  layout :page

  get :index do
    @title = "Klou - Lo dificil no parece facil, es facil!"
    @descrip = "Somos una plataforma de aplicaciones soportada en Google Cloud Engine, la mejor forma de desarrollar e implementar aplicaciones web.  Buscamos agilidad, optimización de tiempo, experiencias únicas. Creemos que la tecnología debe ser fácil, debe ser rápida y entendible para todos."
    erb :"base/index"
  end

  get :about do
    "about"
  end

  get :tos do
    "Terminos de Uso"
  end

  get :privacy do
    "Politicas de Privacidad"
  end

  get :security do
    "Seguridad"
  end

  get :team do
    "Equipo"
  end

  get :pricing do
    "precios"
  end

end
