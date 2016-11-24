package com.carjump.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.carjump.service.webservice.FetchServiceImpl


/**
  * our scheduled actor definition.
  * VERY simple
  */
class ScheduledItemFetchActor extends Actor with ActorLogging {

  def receive = {
    case FetchItems => {
      log.info("collecting web data")
      FetchServiceImpl.persistItems
    }
  }

}

object ScheduledItemFetchActor {
  val props = Props[ScheduledItemFetchActor]

}

object FetchItems



