class UsersController < ApplicationController
  # GET /users
  # GET /users.json
  def index
    @users = User.where("user_role_id = ?", 1).all
    @final = Set.new

    company =Company.find(current_user.company.id)
    shopping_address = company.shopping.address

    @users.each do |user|
      user_address = user.address
      if user_address.distance_to([shopping_address.latitude,shopping_address.longitude]) > 200
        @users.remove(user)
      end
    end

    @users.each do |user|
      user.keywords.each do |keyword|
        if company.keywords.include?(keyword)
          user.directed_offers = Offer.where("user_id = ?", user.id).all
          @final.add(user)
        end
      end
    end

    @users = @final

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @users, :include => {:directed_offers => {:include => :company}} }
    end
  end

  # GET /users/1
  # GET /users/1.json
  def show
    @user = User.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @user }
    end
  end

  # GET /users/new
  # GET /users/new.json
  def new
    @user = User.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @user }
    end
  end

  # GET /users/1/edit
  def edit
    @user = User.find(params[:id])
  end

  # POST /users
  # POST /users.json
  def create
    @user = User.new(params[:user])

    respond_to do |format|
      if @user.save
        format.html { redirect_to @user, notice: 'User was successfully created.' }
        format.json { render json: @user, status: :created, location: @user }
      else
        format.html { render action: "new" }
        format.json { render json: @user.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /users/1
  # PUT /users/1.json
  def update
    @user = User.find(params[:id])

    respond_to do |format|
      if @user.update_attributes(params[:user].except(:redirect))
        format.html {
          if params[:user][:redirect]
            redirect_to params[:user][:redirect]
          else
            redirect_to @user, notice: 'User was successfully updated.'
          end
        }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @user.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /users/1
  # DELETE /users/1.json
  def destroy
    @user = User.find(params[:id])
    @user.destroy

    respond_to do |format|
      format.html { redirect_to users_url }
      format.json { head :no_content }
    end
  end

  def update_address

    lat = params[:lat]
    lng = params[:lng]
    user = User.find(params[:id])

    if user.address.nil?
      address = Address.new(:user_id => user.id, :latitude => lat, :longitude => lng, :gmaps => true)
      address.save
    else
      user.address.latitude = lat
      user.address.longitude = lng
      user.address.save
    end

    render :nothing => true, :status => 200, :content_type => 'text/html'

  end
end
