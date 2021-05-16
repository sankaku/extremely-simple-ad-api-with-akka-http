package infra.delivery.dao

import akka.actor
import domain.common.values.CustomError
import domain.common.values.DatabaseConnectionError
import domain.common.values.DuplicatedCvError
import domain.common.values.UndeliveredCvError
import domain.delivery.repositories.AdRepository
import domain.delivery.values.AdId
import redis.RedisClientPool
import redis.RedisServer

import javax.inject.Inject
import javax.inject.Singleton
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

@Singleton
class AdDao @Inject() (implicit ec: ExecutionContext) extends AdRepositoryImpl

class AdRepositoryImpl @Inject() (implicit ec: ExecutionContext) extends AdRepository {
  implicit val akkaSystem: actor.ActorSystem = akka.actor.ActorSystem()
  val redisServer: RedisServer               = RedisServer()
  val pool: RedisClientPool                  = RedisClientPool(Seq(redisServer))

  val FieldName     = "cv"
  val BeforeCvValue = "false"
  val AfterCvValue  = "true"

  override def create(): Future[Either[CustomError, AdId]] = {
    val adId = AdId.create()
    val key  = getKey(adId)
    pool
      .hset(key, FieldName, BeforeCvValue)
      .map(_ => Right[CustomError, AdId](adId))
      .recover { case e =>
        Left[CustomError, AdId](DatabaseConnectionError(message = e.toString))
      }
  }

  override def update(adId: AdId): Future[Either[CustomError, Unit]] = {
    val key = getKey(adId)
    val result = for {
      currentValue <- pool.hget[String](key, FieldName)
      r <- currentValue match {
        case Some(BeforeCvValue) => pool.hset(key, FieldName, AfterCvValue).map(_ => Right[CustomError, Unit]())
        case Some(_) =>
          Future.successful(
            Left[CustomError, Unit](DuplicatedCvError(message = s"Duplicated cv error. id = ${adId.value.toString}"))
          )
        case None =>
          Future.successful(
            Left[CustomError, Unit](UndeliveredCvError(message = s"Undelivered cv error. id = ${adId.value.toString}"))
          )
      }
    } yield r

    result.recover { case e =>
      Left[CustomError, Unit](DatabaseConnectionError(message = e.toString))
    }
  }

  private def getKey(adId: AdId): String = adId.value.toString
}
