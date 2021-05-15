package domain.delivery.services

import domain.common.values.CustomError
import domain.delivery.values.AdId
import domain.delivery.values.CvResponse
import domain.delivery.values.DeliveryResponse

import scala.concurrent.Future

trait AdService {
  def delivery(): Future[Either[CustomError, DeliveryResponse]]

  def cv(id: AdId): Future[Either[CustomError, CvResponse]]
}
