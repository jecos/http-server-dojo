
import MiniHttpServer.Routes
import com.sun.net.httpserver.HttpExchange

class MiniHttpServer(val routes: Routes, override val socketAddress: String = "127.0.0.1", override val port: Int = 8080) extends BaseHttpServer(socketAddress, port) {

  def handle(exchange: HttpExchange): Unit = {
    val action = routes.orElse(notFound).andThen(respond(exchange))
    try {
      action(exchange)
    } catch {
      case ex: Exception => respond(exchange)(InternalError(ex.getMessage))
    }

  }

  private val notFound: Routes = {
    case _ => NotFound()
  }


}

object MiniHttpServer {
  type Routes = PartialFunction[HttpExchange, Response]

  def apply(socketAddress: String = "127.0.0.1",
            port: Int = 8080)(definedRoutes: Routes) = new MiniHttpServer(definedRoutes, socketAddress, port)

}