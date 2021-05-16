package domain.delivery.values

import domain.common.values.CustomError

sealed trait ApiResponse {
  def success: Boolean

  def errors: Seq[CustomError]

  def message: ApiMessage
}

final case class DeliveryResponse(
  override val success: Boolean,
  override val errors: Seq[CustomError] = Nil,
  override val message: DeliveryMessage = DeliveryMessage(Nil))
    extends ApiResponse

final case class CvResponse(
  override val success: Boolean,
  override val errors: Seq[CustomError] = Nil,
  override val message: CvMessage = CvMessage(None))
    extends ApiResponse
