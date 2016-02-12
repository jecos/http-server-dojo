
import _root_.MiniHttpServer.Routes
import com.sun.net.httpserver.HttpExchange



object MiniHttpServer {
  type Routes = PartialFunction[HttpExchange, Response]

  def apply(routes: Routes) = new MiniHttpServer {
    override def routes: PartialFunction[HttpExchange, Response] = routes
  }

}



abstract class MiniHttpServer extends BaseHttpServer {
  /**
   * Handle a HTTP request
   */
  def handle(exchange: HttpExchange): Unit = {
    val action = routes.orElse(notFound).andThen(respond(exchange))
    try {
      action(exchange)
    } catch {
      case ex: Exception => respond(exchange)(InternalError(ex.toString))
    }

  }

  private val notFound: Routes = {
    case _ => NotFound
  }

  def routes: Routes


}