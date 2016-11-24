package com.carjump.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.carjump.actor.{FetchItems, ScheduledItemFetchActor}
import com.carjump.route.Item
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._
import scala.language.postfixOps

/**
  * Where everything starts
  * Besides binding servers and routes, I am triggering the scheduled actor, based on interval received
  * at application.conf
  *
  */
object ServerRunner extends App  {
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val context = system.dispatcher


  val config = ConfigFactory.load()
  val bindingFuture = Http().bindAndHandle(Item.route, config.getString("http.interface"), config.getInt("http.port"))

  val scheduleActor = system.actorOf(ScheduledItemFetchActor.props, "scheduleActor")
  system.scheduler.schedule(0 seconds, config.getInt("interval") seconds, scheduleActor, FetchItems)


}





