import com.sun.net.httpserver.HttpExchange

object p {
  def unapply(exchange: HttpExchange): Option[String] = ???

}