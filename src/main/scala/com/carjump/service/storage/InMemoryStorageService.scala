package com.carjump.service.storage

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Simple in memory cache implementation. Not being used.
  * Please see current solution in place @link com.carjump.service.storage.CompressionStorageService
  */
class InMemoryStorageService extends StorageService {
  val cache = collection.mutable.ArrayBuffer[Char]()

  /**
    * in memory persistence implementation.
    * Add all items sequentially to our cache
    *
    * @param items The list of items to be stored
    */
  override def persist(items: Seq[Char]): Unit = {
    cache.clear

    items foreach (item => cache += item)

  }

  /**
    * definition of how we retrieve a single item
    *
    * @param index the position to be queried for
    * @return the char at index position, if it exists
    */
  override def get(index: Int): Future[Option[Char]] = {

    val result = index match {
      case x: Int if (x > cache.size) => None
      case x: Int if (x < 0) => None
      case _ => Some(cache(index))
    }

    Future {
      result
    }
  }
}
