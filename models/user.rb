class User
  include Mongoid::Document
  include Mongoid::Timestamps

  field :email, :type => String
  
  field :password, :type => String
  field :salt, :type => String

  field :status, :type => Integer

  field :last_login_ip, type: String
  field :last_login_date, type: Time
  field :current_login_ip, type: String
  field :current_login_date, type: Time
  
  field :api_key, type: String 

  has_many :projects
  has_many :keys
  validates_uniqueness_of :email

  #VERIFICACION DE AUTENTIFICACION
  def authenticate(pass)
    if(hash_password(pass, salt)).eql?(password)
      true
    else
      false
    end
  end

end
