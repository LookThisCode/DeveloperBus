class API::V1::DistributorController < ApplicationController
	def index
    @distributors = Distribuidor.all
    values = { :distributors => @distributors }
    respond_to do |format|
      format.json { render :json => values }
    end
  end
end