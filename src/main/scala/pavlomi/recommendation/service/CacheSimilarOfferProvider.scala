package pavlomi.recommendation.service

import pavlomi.recommendation.cache.CacheProvider
import pavlomi.recommendation.domain.SimilarOffer

import scala.collection.JavaConverters._

class CacheSimilarOfferProvider(cacheProvider: CacheProvider) extends SimilarOfferProvider {

  private lazy val command = cacheProvider.defaultConnection.sync()

  override def getSimilarOffer(offerId: Int): Seq[SimilarOffer] =
    command
      .zrangeWithScores("content_matrix:" + offerId, 0, -1)
      .asScala
      .map(scoredValue => SimilarOffer(offerId, scoredValue.getValue.toInt, scoredValue.getScore))

}
