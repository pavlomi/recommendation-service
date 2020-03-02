package pavlomi.recommendation.service

import pavlomi.recommendation.domain.FullOffer

trait FullOfferProvider {
  def getFullOffer(offerIds: Seq[Int]): Seq[FullOffer]
}

object FullOfferProvider {
  val EMPTY = new FullOfferProvider {
    override def getFullOffer(offerIds: Seq[Int]): Seq[FullOffer] = Seq.empty
  }
}
