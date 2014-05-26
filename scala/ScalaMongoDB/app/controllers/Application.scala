package controllers

import play.api._
import play.api.mvc._


object Application extends Controller {
  
  def index = Action { implicit request => 
  	request.session.get("connected").map{
  		print("no user")
  		user => Ok(views.html.index())
  	}.getOrElse {
  		Unauthorized("Oops, you are not connected")
  	}
  }

}