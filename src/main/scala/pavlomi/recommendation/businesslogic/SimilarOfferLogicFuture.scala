package pavlomi.recommendation.businesslogic

import cats.~>
import pavlomi.recommendation.businesslogic.SimilarOfferProgram.RecommendationA
import pavlomi.recommendation.service.{FullOfferProvider, RecommendationSorterFilter, SimilarOfferProvider}

import scala.concurrent.Future

class SimilarOfferLogicFuture(
  similarOfferProvider: SimilarOfferProvider,
  fullOfferProvider: FullOfferProvider,
  recommendationSorterFilter: RecommendationSorterFilter
) extends (RecommendationA ~> Future) {

  import pavlomi.recommendation.businesslogic.SimilarOfferProgram._

  def apply[A](fa: RecommendationA[A]): Future[A] =
    fa match {
      case GetSimilarOffer(offerId)    => Future.successful(similarOfferProvider.getSimilarOffer(offerId))
      case GetFullOffer(offerIds)      => Future.successful(fullOfferProvider.getFullOffer(offerIds))
      case SortFilterOffer(fullOffers) => Future.successful(recommendationSorterFilter.sortFilter(fullOffers))
    }
}
