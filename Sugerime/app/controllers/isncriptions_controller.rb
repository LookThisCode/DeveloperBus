class IsncriptionsController < ApplicationController
  # GET /isncriptions
  # GET /isncriptions.json
  def index
    @isncriptions = Isncription.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @isncriptions }
    end
  end

  # GET /isncriptions/1
  # GET /isncriptions/1.json
  def show
    @isncription = Isncription.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @isncription }
    end
  end

  # GET /isncriptions/new
  # GET /isncriptions/new.json
  def new
    @isncription = Isncription.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @isncription }
    end
  end

  # GET /isncriptions/1/edit
  def edit
    @isncription = Isncription.find(params[:id])
  end

  # POST /isncriptions
  # POST /isncriptions.json
  def create
    @isncription = Isncription.new(params[:isncription])

    respond_to do |format|
      if @isncription.save
        format.html { redirect_to @isncription, notice: 'Isncription was successfully created.' }
        format.json { render json: @isncription, status: :created, location: @isncription }
      else
        format.html { render action: "new" }
        format.json { render json: @isncription.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /isncriptions/1
  # PUT /isncriptions/1.json
  def update
    @isncription = Isncription.find(params[:id])

    respond_to do |format|
      if @isncription.update_attributes(params[:isncription])
        format.html { redirect_to @isncription, notice: 'Isncription was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @isncription.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /isncriptions/1
  # DELETE /isncriptions/1.json
  def destroy
    @isncription = Isncription.find(params[:id])
    @isncription.destroy

    respond_to do |format|
      format.html { redirect_to isncriptions_url }
      format.json { head :no_content }
    end
  end
end
