package domain.common.values

sealed trait CustomError

final case class NotFoundError(
  message: String)
    extends CustomError

final case class DatabaseConnectionError(
  message: String)
    extends CustomError

final case class DuplicatedCvError(
  message: String)
    extends CustomError

final case class UndeliveredCvError(
  message: String)
    extends CustomError

final case class UnknownError(
  message: String)
    extends CustomError
