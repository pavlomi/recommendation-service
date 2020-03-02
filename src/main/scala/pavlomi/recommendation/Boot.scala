package pavlomi.recommendation

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.io.StdIn
import cats.implicits._
import pavlomi.recommendation.businesslogic.SimilarOfferLogicFuture
import pavlomi.recommendation.cache.CacheProvider
import pavlomi.recommendation.router.RecommendationRoute
import pavlomi.recommendation.service.{FullOfferProvider, RecommendationSorterFilter, SimilarOfferProvider}

import scala.collection.JavaConverters._

object Boot extends App {
  implicit val system           = ActorSystem("my-system")
  implicit val materializer     = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val config = CacheProvider.CacheProviderConfig("localhost", 6379)

  val cacheProvider = CacheProvider.create(config)

  val command = cacheProvider.defaultConnection.sync()
  val offerIds = command.lrange("offers_id", 0 , 60000).asScala
  println(offerIds.distinct.size)

  val similarOfferLogicFuture = new SimilarOfferLogicFuture(SimilarOfferProvider.EMPTY, FullOfferProvider.EMPTY, RecommendationSorterFilter.EMPTY)

  val  route = (new RecommendationRoute).route

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}
