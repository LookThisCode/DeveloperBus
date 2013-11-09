require 'test_helper'

class LugaresControllerTest < ActionController::TestCase
  setup do
    @lugare = lugares(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:lugares)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create lugare" do
    assert_difference('Lugare.count') do
      post :create, lugare: { campania_id: @lugare.campania_id, id: @lugare.id, lugar_nombre: @lugare.lugar_nombre }
    end

    assert_redirected_to lugare_path(assigns(:lugare))
  end

  test "should show lugare" do
    get :show, id: @lugare
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @lugare
    assert_response :success
  end

  test "should update lugare" do
    put :update, id: @lugare, lugare: { campania_id: @lugare.campania_id, id: @lugare.id, lugar_nombre: @lugare.lugar_nombre }
    assert_redirected_to lugare_path(assigns(:lugare))
  end

  test "should destroy lugare" do
    assert_difference('Lugare.count', -1) do
      delete :destroy, id: @lugare
    end

    assert_redirected_to lugares_path
  end
end
