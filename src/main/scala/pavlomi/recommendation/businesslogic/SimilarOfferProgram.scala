package pavlomi.recommendation.businesslogic

import cats.free.Free
import cats.free.Free.liftF
import cats.{~>, Id}
import pavlomi.recommendation.domain.{FullOffer, Recommendation, SimilarOffer}
import pavlomi.recommendation.service.{FullOfferProvider, PlainTextSimilarOfferProvider, RecommendationSorterFilter}

import scala.concurrent.Future

object SimilarOfferProgram {

  val similarOfferProvider       = new PlainTextSimilarOfferProvider
  val fullOfferProvider          = FullOfferProvider.EMPTY
  val recommendationSorterFilter = RecommendationSorterFilter.EMPTY

  sealed trait RecommendationA[A]
  case class GetSimilarOffer(offerId: Int)               extends RecommendationA[Seq[SimilarOffer]]
  case class GetFullOffer(offerIds: Seq[Int])            extends RecommendationA[Seq[FullOffer]]
  case class SortFilterOffer(fullOffers: Seq[FullOffer]) extends RecommendationA[Seq[Recommendation]]

  type GetRecommendation[A] = Free[RecommendationA, A]

  def getSimilarOffer(offerId: Int): GetRecommendation[Seq[SimilarOffer]]                 = liftF(GetSimilarOffer(offerId))
  def getFullOffer(offerIds: Seq[Int]): GetRecommendation[Seq[FullOffer]]                 = liftF(GetFullOffer(offerIds))
  def sortFilterOffer(fullOffers: Seq[FullOffer]): GetRecommendation[Seq[Recommendation]] = liftF(SortFilterOffer(fullOffers))

  def program(offerId: Int): GetRecommendation[Seq[Recommendation]] =
    for {
      similarOffer    <- getSimilarOffer(offerId)
      fullOffers      <- getFullOffer(similarOffer.map(_.similarOfferId))
      recommendations <- sortFilterOffer(fullOffers)
    } yield recommendations

  val impureCompiler: RecommendationA ~> Id =
    new (RecommendationA ~> Id) {

      def apply[A](fa: RecommendationA[A]): Id[A] =
        fa match {
          case GetSimilarOffer(offerId)    => similarOfferProvider.getSimilarOffer(offerId)
          case GetFullOffer(offerIds)      => fullOfferProvider.getFullOffer(offerIds)
          case SortFilterOffer(fullOffers) => recommendationSorterFilter.sortFilter(fullOffers)
        }
    }
}
