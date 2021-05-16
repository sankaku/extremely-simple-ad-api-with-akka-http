package controllers.helper

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import domain.common.values.marshallers.CustomErrorMarshaller
import domain.delivery.entities.Ad
import domain.delivery.values.CvMessage
import domain.delivery.values.CvResponse
import domain.delivery.values.DeliveryMessage
import domain.delivery.values.DeliveryResponse
import domain.delivery.values.marshallers.AdIdMarshaller
import spray.json.DefaultJsonProtocol

trait AdJsonSupport extends SprayJsonSupport with DefaultJsonProtocol with AdIdMarshaller with CustomErrorMarshaller {
  implicit val adFormat = jsonFormat1(Ad)

  implicit val deliveryMessageFormat = jsonFormat1(DeliveryMessage)

  implicit val deliveryResponseFormat = jsonFormat3(DeliveryResponse)

  implicit val cvMessageFormat = jsonFormat1(CvMessage)

  implicit val cvResponseFormat = jsonFormat3(CvResponse)
}
