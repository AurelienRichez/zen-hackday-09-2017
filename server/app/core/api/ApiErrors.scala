package core.api

import play.api.libs.json.JsError
import play.api.mvc.Results._
import play.api.i18n.Messages

object ApiErrors {
  def invalidAuth(implicit messages: Messages) =
    ApiError(Unauthorized, key = "apierror.unauthorized")

  def noRights(implicit messages: Messages) =
    ApiError(Unauthorized, key = "apierror.norights")

  def badRequest(arg: String)(implicit messages: Messages) =
    ApiError(BadRequest, key = "apierror.badrequest", arg)

  def invalidFormat(implicit messages: Messages) =
    ApiError(BadRequest, key = "apierror.badformat")

  def forbidden(arg: String)(implicit messages: Messages) =
    ApiError(Forbidden, key = "apierror.forbidden", arg)

  def notFound(implicit messages: Messages) =
    ApiError(NotFound, key = "apierror.notfound")

  def routeNotFound(method: String, path: String)(implicit messages: Messages) =
    ApiError(NotFound, key = "apierror.routenotfound", method, path)

  def validationError(errors: JsError)(implicit messages: Messages) =
    ApiError(BadRequest, key = "apierror.validationerrors", errors)

  def internalServerError(implicit messages: Messages) =
    ApiError(InternalServerError, key = "apierror.internalservererror")
}
