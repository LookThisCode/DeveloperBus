require 'test_helper'

class UserRolesControllerTest < ActionController::TestCase
  setup do
    @user_role = user_roles(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:user_roles)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create user_role" do
    assert_difference('UserRole.count') do
      post :create, user_role: { name: @user_role.name }
    end

    assert_redirected_to user_role_path(assigns(:user_role))
  end

  test "should show user_role" do
    get :show, id: @user_role
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @user_role
    assert_response :success
  end

  test "should update user_role" do
    put :update, id: @user_role, user_role: { name: @user_role.name }
    assert_redirected_to user_role_path(assigns(:user_role))
  end

  test "should destroy user_role" do
    assert_difference('UserRole.count', -1) do
      delete :destroy, id: @user_role
    end

    assert_redirected_to user_roles_path
  end
end
