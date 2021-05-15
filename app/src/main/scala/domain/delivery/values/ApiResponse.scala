package domain.delivery.values

sealed trait ApiResponse {
  def success: Boolean

  def errors: Seq[String]

  def message: ApiMessage
}

final case class DeliveryResponse(
  override val success: Boolean,
  override val errors: Seq[String],
  override val message: DeliveryMessage)
    extends ApiResponse

final case class CvResponse(
  override val success: Boolean,
  override val errors: Seq[String],
  override val message: CvMessage)
    extends ApiResponse
