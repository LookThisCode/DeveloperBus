require 'test_helper'

class CampaniaControllerTest < ActionController::TestCase
  setup do
    @campanium = campania(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:campania)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create campanium" do
    assert_difference('Campanium.count') do
      post :create, campanium: { id: @campanium.id, titulo: @campanium.titulo }
    end

    assert_redirected_to campanium_path(assigns(:campanium))
  end

  test "should show campanium" do
    get :show, id: @campanium
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @campanium
    assert_response :success
  end

  test "should update campanium" do
    put :update, id: @campanium, campanium: { id: @campanium.id, titulo: @campanium.titulo }
    assert_redirected_to campanium_path(assigns(:campanium))
  end

  test "should destroy campanium" do
    assert_difference('Campanium.count', -1) do
      delete :destroy, id: @campanium
    end

    assert_redirected_to campania_path
  end
end
