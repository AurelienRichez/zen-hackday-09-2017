import com.marcospereira.play.i18n.HoconMessagesApiProvider
import controllers.AssetsComponents
import play.api.{ApplicationLoader, BuiltInComponentsFromContext, LoggerConfigurator}
import play.filters.HttpFiltersComponents
import play.api.i18n._
import play.api.libs.ws.ahc.AhcWSComponents
import play.api.routing.Router
import router.Routes
import user.controllers.authentication.Auth

class CustomApplicationLoader extends ApplicationLoader {
  def load(context: ApplicationLoader.Context) = {
    LoggerConfigurator(context.environment.classLoader).foreach {
      _.configure(context.environment)
    }
    new CustomComponents(context).application
  }
}

class CustomComponents(context: ApplicationLoader.Context)
    extends BuiltInComponentsFromContext(context)
    with HttpFiltersComponents
    with AssetsComponents
    with I18nComponents
    with AhcWSComponents {

  // configuration
  val conf = core.utils.Configuration(configuration)
  val settings = new core.utils.Settings(conf)
  core.metrics.Metrics.start(applicationLifecycle, conf)

  // override messagesApi to use Hocon config
  override lazy val messagesApi =
    new HoconMessagesApiProvider(environment, configuration, langs, httpConfiguration).get

  // use this ws client to benefit from automatic metrics and logging
  lazy val instrumentedWSClient = core.metrics.WSClientInstrumented(wsClient)

  // executionContexts
  val defaultEc = actorSystem.dispatcher
  val databaseEc = actorSystem.dispatchers.lookup("db-context")

  // services
  lazy val apiKeyService = new user.services.ApiKeyService()

  lazy val auth = new Auth(settings, apiKeyService)(defaultEc, materializer, messagesApi, controllerComponents)

  lazy val router: Router = new Routes(
    httpErrorHandler,
    new user.controllers.HomeController(controllerComponents, instrumentedWSClient)(defaultEc),
    assets
  )
}
