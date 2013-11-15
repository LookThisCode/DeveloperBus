class LugaresController < ApplicationController
  # GET /lugares
  # GET /lugares.json
  skip_before_filter :authenticate
  def index
    @lugares = Lugare.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @lugares }
    end
  end

  # GET /lugares/1
  # GET /lugares/1.json
  def show
    @lugare = Lugare.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @lugare }
    end
  end

  # GET /lugares/new
  # GET /lugares/new.json
  def new
    @lugare = Lugare.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @lugare }
    end
  end

  # GET /lugares/1/edit
  def edit
    @lugare = Lugare.find(params[:id])
  end

  # POST /lugares
  # POST /lugares.json
  def create
    @lugare = Lugare.new(params[:lugare])

    respond_to do |format|
      if @lugare.save
        format.html { redirect_to @lugare, notice: 'Lugare was successfully created.' }
        format.json { render json: @lugare, status: :created, location: @lugare }
      else
        format.html { render action: "new" }
        format.json { render json: @lugare.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /lugares/1
  # PUT /lugares/1.json
  def update
    @lugare = Lugare.find(params[:id])

    respond_to do |format|
      if @lugare.update_attributes(params[:lugare])
        format.html { redirect_to @lugare, notice: 'Lugare was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @lugare.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /lugares/1
  # DELETE /lugares/1.json
  def destroy
    @lugare = Lugare.find(params[:id])
    @lugare.destroy

    respond_to do |format|
      format.html { redirect_to lugares_url }
      format.json { head :no_content }
    end
  end
end
