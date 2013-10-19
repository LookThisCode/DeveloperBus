ActiveAdmin.register Company do

  form do |f|
    f.inputs "Empresa" do
      f.input :name
      f.input :shopping_id, :as => :select, :collection => Shopping.all
      f.input :user_id, :as => :select, :collection => User.where(:user_role_id => 2).all
      f.input :keywords, :as => :check_boxes

    end

    f.actions
  end


end