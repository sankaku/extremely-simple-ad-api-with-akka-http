package controllers.helper

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import domain.delivery.values.marshallers.AdIdMarshaller
import domain.delivery.values.CvMessage
import domain.delivery.values.CvResponse
import domain.delivery.values.DeliveryMessage
import domain.delivery.values.DeliveryResponse
import spray.json.DefaultJsonProtocol

trait AdJsonSupport extends SprayJsonSupport with DefaultJsonProtocol with AdIdMarshaller {
  implicit val deliveryMessageFormat = jsonFormat1(DeliveryMessage)

  implicit val deliveryResponseFormat = jsonFormat3(DeliveryResponse)

  implicit val cvMessageFormat = jsonFormat1(CvMessage)

  implicit val cvResponseFormat = jsonFormat3(CvResponse)
}
