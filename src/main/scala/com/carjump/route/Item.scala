package com.carjump.route

import akka.http.scaladsl.model.{HttpResponse, StatusCode}
import akka.http.scaladsl.server.Directives._
import com.carjump.service.webservice.FetchServiceImpl
import akka.http.scaladsl.model.StatusCodes.BadRequest

/**
  * our getItem route definition
  */
object Item {

  val route =
    path("item" / IntNumber) { id =>
      get {
        onSuccess(FetchServiceImpl.getItem(id)) { item =>
          item match {
            case None =>  complete(HttpResponse(BadRequest, entity = "No item found for the given index"))
            case Some(e) => complete(e.toString)
          }
        }
      }
    }
}
