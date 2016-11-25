package com.carjump.service.storage

/**
  * Created by dm on 23.11.16.
  */
trait StorageService {

  /**
    * definition of how we persist our items
    *
    * @param items The list of items to be stored
    */
  def persist(items: Seq[Char]): Unit

  /**
    * definition of how we retrieve a single item
    *
    * @param index the position to be queried for
    * @return the char at index position, if it exists
    */
  def get(index: Int): Option[Char]


}


