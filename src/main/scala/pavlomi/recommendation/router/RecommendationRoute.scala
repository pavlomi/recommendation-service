package pavlomi.recommendation.router

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.{Directives, Route}
import spray.json._

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol

class RecommendationRoute extends Directives with JsonSupport {

  val route: Route = (get & path("similar-offer" / IntNumber)) { offerId =>
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http test test</h1>"))
  } ~ (get & path("all-offer-ids")) {
    complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "adsfadsfds"))
  }

}
