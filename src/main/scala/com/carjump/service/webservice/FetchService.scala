package com.carjump.service.webservice

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import com.carjump.service.storage.CompressionStorageService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Simple trait to dictate the  actions in our applicaiton
  */
trait FetchService {
  def persistItems: Unit

  def getItem(index: Int): Future[Option[Char]]
}


object FetchServiceImpl extends FetchService {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()

  val URI = "http://challenge.carjump.net/A"

  val storage = new CompressionStorageService

  /**
    * Service responsible for retrieving data from the web and
    * forwarding results for storage
    */
  override def persistItems: Unit = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = URI))

    responseFuture flatMap {
      response => {
        Unmarshal(response).to[Array[Char]] map {
          array => {
            storage.persist(array.filterNot(x => x.equals('\n')))    // removing all line breaks from response
          }
        }
      }
    }
  }

  /**
    * Service responsible for retrieving from our cache the item stored at a certain index
    * @param index The index to be queried for
    * @return the item at the given index
    */
  override def getItem(index: Int): Future[Option[Char]] = storage.get(index)
}