package model

import play.api.libs.json._

case class Person(id: String, name: String, lastName: String, tags: Set[String] = Set())

object Person {
  implicit val jsonFormat: Format[Person] = Json.format[Person]
}
