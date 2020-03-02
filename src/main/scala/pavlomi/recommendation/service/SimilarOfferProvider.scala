package pavlomi.recommendation.service

import pavlomi.recommendation.domain.SimilarOffer

trait SimilarOfferProvider {
  def getSimilarOffer(offerId: Int): Seq[SimilarOffer]
}

object SimilarOfferProvider {
  val EMPTY = new SimilarOfferProvider {
    override def getSimilarOffer(offerId: Int): Seq[SimilarOffer] = Seq.empty
  }
}
