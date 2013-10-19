class User < ActiveRecord::Base
  has_and_belongs_to_many :keywords
  has_one :company
  belongs_to :user_role
  belongs_to :genre
  has_many :inscriptions
  has_one :address

  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable and :omniauthable
  devise :database_authenticatable, :registerable, 
         :recoverable, :rememberable, :trackable, :validatable, :omniauthable, :omniauth_providers => [:google_oauth2]

  # Setup accessible (or protected) attributes for your model
  attr_accessible :email, :password, :password_confirmation, :remember_me, :provider, :uid, :avatar, :keyword_ids
  attr_accessible :birthdate, :email, :last_name, :name, :password, :user_role_id, :address, :address_attributes, :need

  attr_accessor :directed_offers

  accepts_nested_attributes_for :address

  # METHODS ---------------------------------------------
  def self.from_omniauth(auth)
    if user = User.find_by_email(auth.info.email)
      user.provider = auth.provider
      user.uid = auth.uid
      user
    else
      where(auth.slice(:provider, :uid)).first_or_create do |user|
        user.provider = auth.provider
        user.uid = auth.uid
        user.username = auth.info.name
        user.email = auth.info.email
        user.avatar = auth.info.image
        user.name = auth.info.first_name
        user.last_name = auth.info.last_name
      end
    end
  end
end
