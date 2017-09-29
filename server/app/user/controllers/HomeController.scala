package user.controllers

import scala.concurrent.ExecutionContext

import core.utils.Logging
import model.Person
import play.api.libs.ws.WSClient
import play.api.mvc._

class HomeController(cc: ControllerComponents, wsClient: WSClient)(implicit ec: ExecutionContext)
    extends AbstractController(cc)
    with Logging {

  def index = Action {
    logger.debug("index action")
    Ok(core.views.html.index())
  }

  def people = Action {
    Ok(
      Seq(
        Person("Joe", "", Set("red", "bleu")),
        Person("Mary", "", Set("pink", "gray", "bleu")),
        Person("Moe", "", Set("brown", "bleu"))
      ).toString
    )
  }

  def about = Action {
    Ok(sbt.BuildInfo.toString)
  }

  def proxy(url: String) = Action.async { request =>
    wsClient
      .url(url)
      .get()
      .map { response =>
        Ok(response.statusText)
      }
  }
}
