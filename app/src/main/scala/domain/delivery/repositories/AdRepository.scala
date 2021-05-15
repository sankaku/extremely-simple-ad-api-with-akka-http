package domain.delivery.repositories

import domain.common.values.CustomError
import domain.delivery.values.AdId

import scala.concurrent.Future

trait AdRepository {
  def create(): Future[Either[CustomError, AdId]]

  def update(adId: AdId): Future[Either[CustomError, Unit]]
}
