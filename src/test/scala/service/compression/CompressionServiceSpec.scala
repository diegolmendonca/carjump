package service.compression

import com.carjump.service.compression.{CompressionService, Repeat, Single}
import org.specs2.mutable.Specification

/**
  * Created by dm on 24.11.16.
  */
class CompressionServiceSpec extends Specification {

  "CompressionService" should {
    val compressionService = new CompressionService

    "compress properly" in {

      val result = compressionService.compress(Seq('c', 'c', 'c', 'c', 'd', 'c', 'c'))
      result.size should beEqualTo(3)

      val first= result(0).asInstanceOf[Repeat[Char]]
      val second= result(1).asInstanceOf[Single[Char]]
      val third= result(2).asInstanceOf[Repeat[Char]]

      first.count must be equalTo(4)
      first.element should be equalTo('c')
      second.element should be equalTo('d')
      third.count must be equalTo(2)
      third.element should be equalTo('c')

    }



    "decompress properly" in {

      val result = compressionService.decompress(Seq(Repeat(4,'c'),Single('d'),Repeat(2,'c')))

        result.size should beEqualTo(7)
        result(0) should beEqualTo('c')
        result(1) should beEqualTo('c')
        result(2) should beEqualTo('c')
        result(3) should beEqualTo('c')
        result(4) should beEqualTo('d')
        result(5) should beEqualTo('c')
        result(6) should beEqualTo('c')


    }


  }

}
