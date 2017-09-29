package user.controllers

import scala.concurrent.ExecutionContext

import core.utils.Logging
import model.{PersonGenerator}
import play.api.libs.ws.WSClient
import play.api.mvc._
import play.api.libs.json._

class HomeController(cc: ControllerComponents, wsClient: WSClient)(implicit ec: ExecutionContext)
    extends AbstractController(cc)
    with Logging {

  def index = Action {
    logger.debug("index action")
    Ok(core.views.html.index())
  }

  def people(howMany: Int) = Action {
    val personGenerator = PersonGenerator.newPersonGenerator
    val plist = for (i <- 1 to howMany) yield personGenerator.next
    Ok(Json.toJson(plist))
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
