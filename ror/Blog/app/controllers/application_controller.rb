class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  protect_from_forgery with: :exception
  
  def index
    @user = User.all
  end
  
  def person_params
    params.require(:user).permit(:name, :email, :password, :password_confirmation, :remember_me)
  end
  
end
