class Reservation
  include Mongoid::Document

  attr_accessible :place, :date, :user_id, :user

  field :place, type: String
  field :date, type: Date

  belongs_to :user

  def to_hash
    { resident: user.name,
      place: place,
      time: date.strftime("%d de %B de %Y")
    }
  end
end
