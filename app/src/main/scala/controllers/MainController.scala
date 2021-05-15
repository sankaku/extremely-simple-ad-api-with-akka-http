package controllers

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import spray.json._

import scala.io.StdIn

final case class MyResponse(
  status: Int,
  message: String)

trait MyJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val itemFormat = jsonFormat2(MyResponse)
}

object MainController extends MyJsonSupport {

  def main(args: Array[String]): Unit = {

    implicit val system           = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext = system.executionContext

    val route =
      concat(
        path("ping") {
          get {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "pong"))
          }
        },
        path("json") {
          get {
            complete(MyResponse(200, "hello"))
          }
        },
      )

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}
