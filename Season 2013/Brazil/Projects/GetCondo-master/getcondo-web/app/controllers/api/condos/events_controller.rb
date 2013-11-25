class Api::Condos::EventsController < Api::BaseController

  def index
    @events = current_condo.residents.map(&:reservations).flatten
    render json: { result: @events.collect(&:to_hash) }
  end

  def create
    attributes = params.slice(:place, :date).merge(resident: current_user)
    @reservation = Reservation.new(attributes)
    if @reservation.save
      head 201
    else
      head 422
    end
  end

end
