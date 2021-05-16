package controllers.helper

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import domain.delivery.values.DeliveryMessage
import domain.delivery.values.DeliveryResponse
import domain.delivery.values.marshallers.AdIdMarshaller
import spray.json.DefaultJsonProtocol

trait AdJsonSupport extends SprayJsonSupport with DefaultJsonProtocol with AdIdMarshaller {
  implicit val deliveryMessageFormat = jsonFormat1(DeliveryMessage)

  implicit val deliveryResponseFormat = jsonFormat3(DeliveryResponse)
}
