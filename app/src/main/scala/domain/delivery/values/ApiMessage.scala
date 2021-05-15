package domain.delivery.values

sealed trait ApiMessage

final case class DeliveryMessage(
  ads: Seq[AdId])
    extends ApiMessage

final case class CvMessage(
  id: Option[AdId])
    extends ApiMessage
