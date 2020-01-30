import io.vertx.core.AbstractVerticle
import io.vertx.kotlin.core.deploymentOptionsOf
import org.koin.core.context.startKoin

class BootstrapVerticle : AbstractVerticle() {
    override fun start() {
        startKoin {
            chatModule
        }
        vertx.registerVerticleFactory(KoinVerticleFactory)
        vertx.deployVerticle(
            "${KoinVerticleFactory.prefix()}:${ChatVerticle::class.java.canonicalName}",
            deploymentOptionsOf(instances = 3)
        )
    }
}