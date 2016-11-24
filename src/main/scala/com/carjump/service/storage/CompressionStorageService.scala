package com.carjump.service.storage

import com.carjump.service.compression.{Compressed, CompressionService}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

/**
  * Simple in memory cache implementation with compression applied
  * This is the current in place solution
  */
class CompressionStorageService extends StorageService {
  val cache = collection.mutable.ArrayBuffer[Compressed[Char]]()
  val compressionService = new CompressionService

  /**
    * in memory persistence implementation, with compression algorithm applied
    * Add all items sequentially to our cache, after they are compressed
    *
    * @param items The list of items to be stored
    */
  override def persist(items: Seq[Char]): Unit = {
    cache.clear
    val compressedItems = compressionService.compress(items)
    compressedItems foreach (item => cache += item)

  }

  /**
    * definition of how we retrieve a single item
    * List ist first decompressed, then we are able to query for an accurate index position
    *
    * @param index the position to be queried for
    * @return the char at index position, if it exists
    */
  override def get(index: Int): Future[Option[Char]] = {
    val decompressed = compressionService.decompress(cache)

    val result = index match {
      case x: Int if (x > decompressed.size) => None
      case _ => Some(decompressed(index))
    }

    Future {
      result
    }
  }
}
