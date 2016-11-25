package com.carjump.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.carjump.service.webservice.FetchServiceImpl

/**
  * our getItem actor definition.
  */
class GetterActor extends Actor with ActorLogging {

  override def receive = {
    case x: Int =>
      val result = FetchServiceImpl.getItem(x)
      sender() ! GetItemResponse(result)
  }
}

object GetterActor {
  val props = Props[GetterActor]

}

case class GetItemResponse(maybeChar: Option[Char])

