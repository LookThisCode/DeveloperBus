class CampaniaController < ApplicationController
  # GET /campania
  # GET /campania.json
  skip_before_filter :authenticate,:verify_authenticity_token
  
  def index
    @campania = Campanium.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @campania }
    end
  end

  # GET /campania/1
  # GET /campania/1.json
  def show
    @campanium = Campanium.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @campanium }
    end
  end

  # GET /campania/new
  # GET /campania/new.json
  def new
    @campanium = Campanium.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @campanium }
    end
  end

  # GET /campania/1/edit
  def edit
    @campanium = Campanium.find(params[:id])
  end

  # POST /campania
  # POST /campania.json
  def create
    @campanium = Campanium.new(params[:campanium])

    respond_to do |format|
      if @campanium.save
        format.html { redirect_to "http://jm.uvicate.com/circulopyme/#inicio" }
        #format.json { render json: @campanium, status: :created, location: @campanium }
      else
        format.html { render action: "new" }
        format.json { render json: @campanium.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /campania/1
  # PUT /campania/1.json
  def update
    @campanium = Campanium.find(params[:id])

    respond_to do |format|
      if @campanium.update_attributes(params[:campanium])
        format.html { redirect_to @campanium, notice: 'Campanium was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @campanium.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /campania/1
  # DELETE /campania/1.json
  def destroy
    @campanium = Campanium.find(params[:id])
    @campanium.destroy

    respond_to do |format|
      format.html { redirect_to campania_url }
      format.json { head :no_content }
    end
  end
end
