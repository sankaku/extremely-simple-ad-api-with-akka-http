package domain.common.values.marshallers

import domain.common.values.CustomError
import spray.json.DefaultJsonProtocol
import spray.json.JsString
import spray.json.JsValue
import spray.json.RootJsonFormat
import spray.json.deserializationError

trait CustomErrorMarshaller extends DefaultJsonProtocol {
  implicit object CustomErrorJsonFormat extends RootJsonFormat[CustomError] {
    def write(error: CustomError): JsValue = JsString(error.message)

    def read(jsValue: JsValue): CustomError =
      deserializationError(s"Can't unmarshall to CustomError")

    implicit val customErrorJsonFormat: CustomErrorJsonFormat.type = this
  }
}
