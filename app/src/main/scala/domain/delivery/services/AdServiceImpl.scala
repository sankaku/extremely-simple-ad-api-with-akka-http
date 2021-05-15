package domain.delivery.services
import domain.common.values.CustomError
import domain.delivery.repositories.AdRepository
import domain.delivery.values.AdId
import domain.delivery.values.CvMessage
import domain.delivery.values.CvResponse
import domain.delivery.values.DeliveryMessage
import domain.delivery.values.DeliveryResponse

import javax.inject.Inject
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class AdServiceImpl @Inject() (
  adRepository: AdRepository
)(implicit ec: ExecutionContext)
    extends AdService {
  override def delivery(num: Int = 5): Future[Either[DeliveryResponse, DeliveryResponse]] =
    for {
      results <- Future.sequence((0 to num).map(_ => adRepository.create()))
    } yield {
      val eitherAdIds = results
        .foldLeft(Right[CustomError, List[AdId]](Nil): Either[CustomError, List[AdId]]) { (acc, r) =>
          for {
            rR   <- r
            accR <- acc
          } yield rR :: accR
        }
      eitherAdIds.fold(
        e =>
          Left[DeliveryResponse, DeliveryResponse](
            DeliveryResponse(success = false, errors = Seq(e.toString), message = DeliveryMessage(Nil))
          ),
        adIds =>
          Right[DeliveryResponse, DeliveryResponse](
            DeliveryResponse(success = true, errors = Nil, message = DeliveryMessage(adIds))
          )
      )

    }

  override def cv(id: AdId): Future[Either[CvResponse, CvResponse]] =
    for {
      result <- adRepository.update(id)
    } yield result.fold(
      e =>
        Left[CvResponse, CvResponse](CvResponse(success = false, errors = Seq(e.toString), message = CvMessage(None))),
      _ => Right[CvResponse, CvResponse](CvResponse(success = true, errors = Nil, message = CvMessage(Some(id))))
    )
}
