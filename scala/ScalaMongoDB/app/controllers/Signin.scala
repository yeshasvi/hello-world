package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.{User}
import utils.SecurePasswordHashing;

case class LoginForm(email:String, password: String)

object Signin extends Controller {

  val loginForm = Form(
    Forms.mapping(
      "email" -> email, 
      "password" -> nonEmptyText)(LoginForm.apply)(LoginForm.unapply))
  
  def login = Action {
    Ok(views.html.login(loginForm, "Login form"))
  }

  def authenticate = Action{
    implicit request => loginForm.bindFromRequest.fold(
      errors => BadRequest(views.html.login(errors, "There is some error")),
      logInForm => {
        val email = logInForm.email
        val password = logInForm.password
        print(password);
        val users = User.findByEmail(email)
        users.isEmpty match {
          case true => Ok(views.html.login(loginForm, "Invalid Credentials"))
          case false =>
            val userEntity = users(0);
            print(userEntity.password)
            if(SecurePasswordHashing.validatePassword(password, userEntity.password)) {
              print("matched")
              Ok(views.html.userDetail(userEntity)).withSession("connected" -> userEntity.email);  
            } else {
              Ok(views.html.login(loginForm, "Invalid Credentials"))
            }
        }
      })
  }
}