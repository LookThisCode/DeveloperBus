Klou::App.controllers :base, :map => '/' do
  
  layout :page

  get :index do
    @title = "Klou - Lo dificil no parece facil, es facil!"
    @descrip = "Lo dificil no parece facil, es facil!"
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
