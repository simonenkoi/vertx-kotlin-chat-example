import org.koin.dsl.module

val chatModule = module {
        factory { ChatVerticle() }
    }