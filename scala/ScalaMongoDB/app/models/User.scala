package models

import play.api.Play.current
import play.api.PlayException
import com.novus.salat._
import com.novus.salat.dao._
import com.mongodb.casbah.commons.Imports._
import com.mongodb.casbah.MongoConnection
import com.novus.salat.Context
import mongoContext._


case class User(_id: ObjectId = new ObjectId, email: String, password: String)

object UserDAO extends SalatDAO[User, ObjectId](
  collection = MongoConnection()(
    current.configuration.getString("mongodb.default.db")
      .getOrElse(throw new PlayException("Configuration error",
      "Could not find mongodb.default.db in settings"))
  )("users"))


object User {
    def all(): List[User] = UserDAO.find(MongoDBObject.empty).toList

    def findUser(email: String, password: String): List[User] = UserDAO.find(MongoDBObject("email" -> email, "password" -> password)).toList

    def create(email: String, password: String): Option[ObjectId] = UserDAO.insert(User(email = email, password = password))

    def findByEmail(email: String) : List[User] = {
      UserDAO.find(MongoDBObject("email" -> email)).toList
    }

    def delete(id: String) {
        UserDAO.remove(MongoDBObject("_id" -> new ObjectId(id)))
    }
}