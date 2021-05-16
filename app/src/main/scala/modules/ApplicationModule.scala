package modules

import com.google.inject.AbstractModule
import domain.delivery.repositories.AdRepository
import domain.delivery.services.AdService
import domain.delivery.services.AdServiceImpl
import infra.delivery.dao.AdDao

import scala.concurrent.ExecutionContext

class ApplicationModule(implicit ec: ExecutionContext) extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ExecutionContext]).toInstance(ec)
    bind(classOf[AdRepository]).to(classOf[AdDao])
    bind(classOf[AdService]).to(classOf[AdServiceImpl])
  }
}
