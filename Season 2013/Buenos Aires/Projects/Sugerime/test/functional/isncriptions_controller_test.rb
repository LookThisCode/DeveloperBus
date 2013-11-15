require 'test_helper'

class IsncriptionsControllerTest < ActionController::TestCase
  setup do
    @isncription = isncriptions(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:isncriptions)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create isncription" do
    assert_difference('Isncription.count') do
      post :create, isncription: {  }
    end

    assert_redirected_to isncription_path(assigns(:isncription))
  end

  test "should show isncription" do
    get :show, id: @isncription
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @isncription
    assert_response :success
  end

  test "should update isncription" do
    put :update, id: @isncription, isncription: {  }
    assert_redirected_to isncription_path(assigns(:isncription))
  end

  test "should destroy isncription" do
    assert_difference('Isncription.count', -1) do
      delete :destroy, id: @isncription
    end

    assert_redirected_to isncriptions_path
  end
end
