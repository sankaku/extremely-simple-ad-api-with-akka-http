package infra.delivery.dao

import domain.common.values.CustomError
import domain.delivery.repositories.AdRepository
import domain.delivery.values.AdId

import javax.inject.Singleton
import scala.concurrent.Future

@Singleton
class AdDao extends AdRepositoryImpl

class AdRepositoryImpl extends AdRepository {
  override def create(): Future[Either[CustomError, AdId]] = {
    val adId = AdId.create()
    Future.successful(Right(adId))
  }

  override def update(adId: AdId): Future[Either[CustomError, Unit]] =
    Future.successful(Right(()))
}
