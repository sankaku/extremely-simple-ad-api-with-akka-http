package domain.delivery.services
import domain.common.values.CustomError
import domain.common.values.InvalidInputError
import domain.delivery.repositories.AdRepository
import domain.delivery.values._

import java.util.UUID
import javax.inject.Inject
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.util.Success
import scala.util.Try

class AdServiceImpl @Inject() (
  adRepository: AdRepository
)(implicit ec: ExecutionContext)
    extends AdService {
  override def deliver(numString: Option[String]): Future[Either[DeliveryResponse, DeliveryResponse]] = {
    val DefaultNum = 5

    val maybeNum = numString.flatMap(_.toIntOption)
    (numString, maybeNum) match {
      case (Some(_), Some(num)) => deliverAction(num)
      case (Some(_), None) =>
        Future.successful(
          Left(
            DeliveryResponse(
              success = false,
              errors = Seq(InvalidInputError(s"num must be a number. num: $numString"))
            )
          )
        )
      case (_, _) => deliverAction(DefaultNum)
    }
  }

  private[services] def deliverAction(num: Int): Future[Either[DeliveryResponse, DeliveryResponse]] = for {
    results <- Future.sequence((1 to num).map(_ => adRepository.create()))
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
          DeliveryResponse(success = false, errors = Seq(e))
        ),
      adIds =>
        Right[DeliveryResponse, DeliveryResponse](
          DeliveryResponse(success = true, errors = Nil, message = DeliveryMessage(adIds))
        )
    )

  }

  override def cv(idString: String): Future[Either[CvResponse, CvResponse]] =
    Try(AdId(UUID.fromString(idString))) match {
      case Success(adId) => cvAction(adId)
      case _ =>
        Future.successful(
          Left(CvResponse(success = false, errors = Seq(InvalidInputError(s"Invalid id: ${idString.toString}"))))
        )
    }

  private[services] def cvAction(adId: AdId): Future[Either[CvResponse, CvResponse]] =
    for {
      result <- adRepository.update(adId)
    } yield result.fold(
      e => Left[CvResponse, CvResponse](CvResponse(success = false, errors = Seq(e))),
      _ => Right[CvResponse, CvResponse](CvResponse(success = true, errors = Nil, message = CvMessage(Some(adId))))
    )
}
