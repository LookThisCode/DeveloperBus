class Api::Condos::IssuesController < Api::BaseController

  def index
    @issues = current_condo.residents.map(&:issues).flatten
    render json: { result: @issues.collect(&:to_hash) }
  end

  def create
    @issue = Issue.new(user: current_user, description: params[:description], image_data: params[:image_data])
    @issue.save
    head 201
  end

end
