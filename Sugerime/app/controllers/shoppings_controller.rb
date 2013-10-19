class ShoppingsController < ApplicationController
  # GET /shoppings
  # GET /shoppings.json
  def index
    @shoppings = Shopping.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @shoppings }
    end
  end

  # GET /shoppings/1
  # GET /shoppings/1.json
  def show
    @shopping = Shopping.find(params[:id])

    @companies_count = @shopping.companies.all.count

    @user = current_user

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @shopping }
    end
  end

  # GET /shoppings/new
  # GET /shoppings/new.json
  def new
    @shopping = Shopping.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @shopping }
    end
  end

  # GET /shoppings/1/edit
  def edit
    @shopping = Shopping.find(params[:id])
  end

  # POST /shoppings
  # POST /shoppings.json
  def create
    @shopping = Shopping.new(params[:shopping])

    respond_to do |format|
      if @shopping.save
        format.html { redirect_to @shopping, notice: 'Shopping was successfully created.' }
        format.json { render json: @shopping, status: :created, location: @shopping }
      else
        format.html { render action: "new" }
        format.json { render json: @shopping.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /shoppings/1
  # PUT /shoppings/1.json
  def update
    @shopping = Shopping.find(params[:id])

    respond_to do |format|
      if @shopping.update_attributes(params[:shopping])
        format.html { redirect_to @shopping, notice: 'Shopping was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @shopping.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /shoppings/1
  # DELETE /shoppings/1.json
  def destroy
    @shopping = Shopping.find(params[:id])
    @shopping.destroy

    respond_to do |format|
      format.html { redirect_to shoppings_url }
      format.json { head :no_content }
    end
  end

  def show_offers

    @shopping = Shopping.find(params[:id])

    user = current_user

    @companies = @shopping.companies.all

    @companies_count = @companies.count

    @offers = []

    @companies.each do |c|
      user.keywords.all.each do |k|
        if c.keywords.include?(k)
          @offers.concat(c.offers.where("user_id = ? OR user_id is ?", user.id, nil))
          break
        end
      end
    end

    respond_to do |format|
      format.html # show.html.erb
      format.json { render :json => @offers, :include => :company }
    end

  end
end
