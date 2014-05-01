package controllers;

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.{User}
import utils.SecurePasswordHashing;

case class SignupForm(email: String, password: String)

object Signup extends Controller {

	var signupForm = Form(
    	Forms.mapping(
      		"email" -> email, 
      		"password" -> nonEmptyText)(SignupForm.apply)(SignupForm.unapply))

	def signup = Action {
		Ok(views.html.signup(signupForm, "Signup form"))
	}

	def create = Action {
		implicit request => signupForm.bindFromRequest.fold(
			errors => BadRequest(views.html.signup(errors, "Error while creating account")),
			signUpForm => {
				val email = signUpForm.email
				val password = SecurePasswordHashing.hashPassword(signUpForm.password)
				var users = User.findByEmail(email)
				print(users.isEmpty)
				User.findByEmail(email).isEmpty match {
					case false => Ok(views.html.signup(signupForm, "Email is already register with us"))
					case true => 

					User.create(email, password)  
					Ok(views.html.index())
				}

			}
		)
	}
}
