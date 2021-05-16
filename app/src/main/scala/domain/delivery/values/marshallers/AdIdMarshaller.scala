package domain.delivery.values.marshallers

import domain.delivery.values.AdId
import spray.json.DefaultJsonProtocol
import spray.json.JsString
import spray.json.JsValue
import spray.json.RootJsonFormat
import spray.json.deserializationError

import java.util.UUID

trait AdIdMarshaller extends DefaultJsonProtocol {
  implicit object AdIdJsonFormat extends RootJsonFormat[AdId] {
    def write(adId: AdId): JsValue = JsString(adId.id.toString)

    def read(jsValue: JsValue): AdId = jsValue match {
      case JsString(id) => AdId(id = UUID.fromString(id))
      case _            => deserializationError(s"Can't unmarshall jsValue: ${jsValue.toString}")
    }

    implicit val adIdJsonFormat: AdIdJsonFormat.type = this
  }
}
