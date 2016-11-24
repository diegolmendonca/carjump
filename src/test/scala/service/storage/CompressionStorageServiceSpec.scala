package service.storage

import com.carjump.service.storage.CompressionStorageService
import org.specs2.concurrent.ExecutionEnv
import org.specs2.mutable.Specification

/**
  * Created by dm on 24.11.16.
  */
class CompressionStorageServiceSpec(implicit ee: ExecutionEnv) extends Specification {

  val compressionStorageService = new CompressionStorageService


  "CompressionStorageService" should {

    "store in a compressed fashion and retrieve items properly" in {
      compressionStorageService.persist(Seq('c', 'c', 'c', 'c', 'd', 'c', 'c'))

      compressionStorageService.cache.size should be equalTo (3)
      compressionStorageService.get(0) should beSome('c').await
      compressionStorageService.get(4) must beSome('d').await
      compressionStorageService.get(10) must beNone.await
    }


  }


}
