import io.vertx.core.Verticle
import io.vertx.core.spi.VerticleFactory
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.qualifier.TypeQualifier

object KoinVerticleFactory : VerticleFactory, KoinComponent {
    override fun prefix(): String = "koin"

    override fun createVerticle(verticleName: String, classLoader: ClassLoader): Verticle {
        return get(TypeQualifier(Class.forName(verticleName.substringAfter("koin:")).kotlin))
    }
}