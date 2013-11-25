ActiveAdmin.register Expense do

  menu :parent => "Gastos"

  filter :date
  filter :user

  # menu :parent => "Expenses Menu"
  # See permitted parameters documentation:
  # https://github.com/gregbell/active_admin/blob/master/docs/2-resource-customization.md#setting-up-strong-parameters
  #
  # permit_params :list, :of, :attributes, :on, :model
  #
  # or
  #
  # permit_params do
  #  permitted = [:permitted, :attributes]
  #  permitted << :other if resource.something?
  #  permitted
  # end

  show do
    attributes_table do
      row :id
      row :location
      row :amount
      row :description
      row :date
      row :reimbursable
      row :billable
      row :expense_category
      row :expense_group
      row :image do |exp|
        image_tag exp.receipt
      end
    end
  end

  index do
    selectable_column
    column :id
    column :location
    column :amount
    column :date
    column :user
    default_actions
  end

  controller do
    def permitted_params
      params.permit expense: [:expense_category_id, :expense_group_id, :user_id, :location, :amount, :description, :receipt, :date, :billable, :reimbursable]
    end
  end
end
