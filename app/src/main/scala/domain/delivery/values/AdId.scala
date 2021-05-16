package domain.delivery.values

import java.util.UUID

final case class AdId(value: UUID)

object AdId {
  def create(): AdId = AdId(value = UUID.randomUUID())
}
