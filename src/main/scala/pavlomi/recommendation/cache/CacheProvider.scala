package pavlomi.recommendation.cache

import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.{ClientOptions, RedisClient, RedisURI, SocketOptions}

class CacheProvider(config: CacheProvider.CacheProviderConfig) {

  private lazy val redisUri = RedisURI.Builder
    .redis(config.host, config.port)
    .build()

  private lazy val socketOptions = SocketOptions.builder().build()

  private lazy val clientOptions = ClientOptions
    .builder()
    .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS) // Reject commands in disconnected state.
    .socketOptions(socketOptions)
    .build()

  lazy val client: RedisClient = {
    val c = RedisClient.create(redisUri)
    c.setOptions(clientOptions)
    c
  }

  lazy val defaultConnection: StatefulRedisConnection[String, String] = client.connect()

}

object CacheProvider {
  case class CacheProviderConfig(host: String, port: Int)

  def create(config: CacheProviderConfig) = new CacheProvider(config)

}
