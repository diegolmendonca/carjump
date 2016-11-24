package service.storage

import com.carjump.service.storage.InMemoryStorageService
import org.specs2.concurrent.ExecutionEnv
import org.specs2.mutable.Specification

class InMemoryStorageServiceSpec(implicit ee: ExecutionEnv) extends Specification {

  val compressionStorageService = new InMemoryStorageService


  "CompressionStorageService" should {

    "store in a compressed fashion and retrieve items properly" in {
      compressionStorageService.persist(Seq('c', 'c', 'c', 'c', 'd', 'c', 'c'))

      compressionStorageService.cache.size should be equalTo (7)
      compressionStorageService.get(0) should beSome('c').await
      compressionStorageService.get(4) must beSome('d').await
      compressionStorageService.get(10) must beNone.await
    }


  }


}
