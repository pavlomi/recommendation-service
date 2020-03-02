package pavlomi.recommendation.service

import pavlomi.recommendation.domain.SimilarOffer

class PlainTextSimilarOfferProvider extends SimilarOfferProvider {

  override def getSimilarOffer(offerId: Int): Seq[SimilarOffer] = store.getOrElse(store.keys.head, Seq.empty)

  private lazy val store: Map[Int, Seq[SimilarOffer]] = {
    val bufferedSource = scala.io.Source.fromResource("matrix-2.csv")
    val similarOfferList = for (line <- bufferedSource.getLines()) yield {

      val cols = line.split(",").map(_.trim)

      val offerId        = cols(0).toInt
      val similarOfferId = cols(1).toInt
      val score          = cols(2).toDouble

      SimilarOffer(offerId, similarOfferId, score)
    }
    similarOfferList.toSeq.groupBy(_.offerId)
  }

}
