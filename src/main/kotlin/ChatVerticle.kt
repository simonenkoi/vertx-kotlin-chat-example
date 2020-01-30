import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.sockjs.BridgeOptions
import io.vertx.ext.web.handler.sockjs.SockJSHandler
import io.vertx.kotlin.ext.web.handler.sockjs.bridgeOptionsOf
import io.vertx.kotlin.ext.web.handler.sockjs.permittedOptionsOf

class ChatVerticle : AbstractVerticle() {

    override fun start() {
        val ebHandler = createSockJSHandler()
        val router = createRouter(ebHandler)

        vertx.createHttpServer().requestHandler(router).listen(8080)

        val eb = vertx.eventBus()

        eb.consumer<Any>("chat.to.server").handler { message ->
            val timestamp = System.nanoTime()
            eb.publish("chat.to.client", "${timestamp}: ${message.body()}")
        }

    }

    private fun createRouter(sockJSHandler: SockJSHandler): Router? {
        return Router.router(vertx).apply {
            route("/eventbus/*").handler(sockJSHandler)
        }
    }

    // Create the event bus bridge and add it to the router.
    private fun createSockJSHandler(): SockJSHandler {
        // Allow events for the designated addresses in/out of the event bus bridge
        val opts: BridgeOptions = bridgeOptionsOf(
            inboundPermitteds = listOf(
                permittedOptionsOf(
                    address = "chat.to.server"
                )
            ),
            outboundPermitteds = listOf(
                permittedOptionsOf(
                    address = "chat.to.client"
                )
            )
        )
        return SockJSHandler.create(vertx).apply {
            bridge(opts)
        }
    }

}