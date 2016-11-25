package com.carjump.route

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import com.carjump.actor.{GetItemResponse, GetterActor}

/**
  * our getItem route definition
  */
object Item {
  implicit val system = ActorSystem("my-system")

  implicit val duration: Timeout = 20 seconds
  val getActor = system.actorOf(GetterActor.props, "getActor")

  val route =
    path("item" / IntNumber) { id =>
      get {
        onSuccess(getActor ? id) {
          case response: GetItemResponse =>
            response.maybeChar match {
              case Some(e) => complete(e.toString)
              case None => complete(HttpResponse(BadRequest, entity = "No item found for the given index"))
            }
          case _ =>
            complete(StatusCodes.InternalServerError, "unknown error")
        }
      }
    }
}
