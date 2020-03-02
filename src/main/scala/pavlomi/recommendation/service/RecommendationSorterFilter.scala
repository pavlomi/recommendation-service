package pavlomi.recommendation.service

import pavlomi.recommendation.domain.{FullOffer, Recommendation}

trait RecommendationSorterFilter {
  def sortFilter(fullOffers: Seq[FullOffer]): Seq[Recommendation]
}

object  RecommendationSorterFilter {
  val EMPTY = new RecommendationSorterFilter {
    override def sortFilter(fullOffers: Seq[FullOffer]): Seq[Recommendation] = Seq.empty
  }
}
