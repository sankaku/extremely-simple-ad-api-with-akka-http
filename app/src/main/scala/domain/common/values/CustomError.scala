package domain.common.values

sealed trait CustomError {
  def message: String
}

sealed trait ServerError extends CustomError

sealed trait ClientError extends CustomError

final case class NotFoundError(
  override val message: String)
    extends ClientError

final case class DatabaseConnectionError(
  override val message: String)
    extends ServerError

final case class DuplicatedCvError(
  override val message: String)
    extends ClientError

final case class UndeliveredCvError(
  override val message: String)
    extends ClientError

final case class InvalidInputError(
  override val message: String)
    extends ClientError

final case class UnknownServerError(
  override val message: String)
    extends ServerError

final case class UnknownClientError(
  override val message: String)
    extends ClientError
