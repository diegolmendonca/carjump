package com.carjump.service.compression

import scala.annotation.tailrec

/**
  * Trait definition, as specified in the assignment
  */
trait Compression {
  def compress[A]: Seq[A] => Seq[Compressed[A]]

  def decompress[A]: Seq[Compressed[A]] => Seq[A]
}

sealed trait Compressed[+A]

case class Single[A](element: A) extends Compressed[A]

case class Repeat[A](count: Int, element: A) extends Compressed[A]


/**
  * Compression service implementation, following RLE algorithim
  */
class CompressionService extends Compression {


  /**
    * Compression following RLE . Note I create two inner methods, as
    * outer world does not need to be aware of their existence. They are
    * auxiliary defs for outermost method completion
    *
    * @return function that transforms seq into compressed seq
    */
  override def compress[A]: Seq[A] => Seq[Compressed[A]] = {

      /**
        * method responsible for counting how many consecutives char exist,
        * given a char and a seq
        *
        * @param char        the char to be compared
        * @param sequence    the seq to be iterated
        * @param accumulator outuput for our tail recursive function
        * @return int containing amount of consecutive occurrences
        */
      @tailrec
      def findConsecutive(char: A, sequence: Seq[A], accumulator: Int): Int = {

        if (sequence.isEmpty) accumulator

        else {
          val head = sequence.head
          if (char.equals(head)) findConsecutive(head, sequence.tail, accumulator + 1) else accumulator
        }

      }


      /**
        * method responsible for returning compressed output
        *
        * @param s the seq to be compressed
        * @return the compressed seq
        */
      def performCompression(s: Seq[A]): Seq[Compressed[A]] = {

        @tailrec
        def iterateOverSequence(sequence: Seq[A], accumulator: Seq[Compressed[A]]): Seq[Compressed[A]] = {

          if (sequence.isEmpty) accumulator

          else {
            val head = sequence.head
            val occurrences = findConsecutive(head, sequence, 0)
            val tail = sequence.drop(occurrences)

            if (occurrences > 1)
              iterateOverSequence(tail, accumulator :+ Repeat(occurrences, head))
            else
              iterateOverSequence(tail, accumulator :+ Single(head))
          }
        }

        iterateOverSequence(s, Seq.empty[Compressed[A]])

      }


    (s: Seq[A]) => {
      performCompression(s)
    }

  }

  /**
    * Decompression following RLE
    *
    * @return function that transforms compressed seq into seq
    */
  override def decompress[A]: (Seq[Compressed[A]]) => Seq[A] = {

    (compressed: Seq[Compressed[A]]) =>
      compressed flatMap {
        item => item match {
          case repeat: Repeat[A] => for (i <- 0 until repeat.count) yield repeat.element
          case single: Single[A] => Seq(single.element)
        }
      }
  }
}
