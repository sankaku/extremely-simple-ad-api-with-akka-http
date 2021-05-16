package domain.delivery.values

import domain.delivery.entities.Ad

sealed trait ApiMessage

final case class DeliveryMessage(
  ads: Seq[Ad])
    extends ApiMessage

final case class CvMessage(
  id: Option[AdId])
    extends ApiMessage
