package domain.delivery.values

import java.util.UUID

final case class AdId(id: UUID)

object AdId {
  def create(): AdId = AdId(id = UUID.randomUUID())
}
