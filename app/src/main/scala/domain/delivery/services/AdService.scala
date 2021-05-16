package domain.delivery.services

import domain.delivery.values.CvResponse
import domain.delivery.values.DeliveryResponse

import scala.concurrent.Future

trait AdService {
  def deliver(numString: Option[String]): Future[Either[DeliveryResponse, DeliveryResponse]]

  def cv(idString: String): Future[Either[CvResponse, CvResponse]]
}
