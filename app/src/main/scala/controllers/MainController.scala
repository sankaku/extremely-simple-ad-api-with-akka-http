package controllers

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.google.inject.Guice.createInjector
import controllers.helper.AdJsonSupport
import domain.delivery.services.AdService
import modules.ApplicationModule

import javax.inject.Inject
import scala.concurrent.ExecutionContext
import scala.io.StdIn

object MainController {

  def main(args: Array[String]): Unit = {

    implicit val system           = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext = system.executionContext

    val injector = createInjector(new ApplicationModule()(executionContext))

    val controller = injector.getInstance(classOf[MainController])
    val routes     = controller.routes

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(routes)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}

class MainController @Inject() (
  adService: AdService
)(implicit ec: ExecutionContext)
    extends AdJsonSupport {

  def routes: Route =
    concat(
      path("ping") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "pong"))
        }
      },
      path("deliver") {
        (get & parameter("num".optional)) { num =>
          complete(adService.deliver(num))
        }
      },
      path("cv") {
        (post & parameter("id")) { id =>
          complete(adService.cv(id))
        }
      },
    )
}
