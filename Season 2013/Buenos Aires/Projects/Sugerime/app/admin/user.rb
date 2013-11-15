ActiveAdmin.register User do

  form do |f|
    f.inputs "User details" do
      f.input :email
      f.input :password
      f.input :password_confirmation
      f.input :name
      f.input :last_name
      f.input :birthdate
      f.input :user_role, :as => :select
    end
    f.actions
  end


  index do
    column :id
    column :email
    column :name
    column :last_name
    column :birthdate
    default_actions
  end
end