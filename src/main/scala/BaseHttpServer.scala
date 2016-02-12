import java.net.InetSocketAddress

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer

import scala.util.{Failure, Success, Try}

abstract class BaseHttpServer(val socketAddress: String = "127.0.0.1",
                              val port: Int = 8080,
                              val backlog: Int = 0) extends HttpHandler {
  private val address = new InetSocketAddress(socketAddress, port)
  private val server = MiniHttpServer.create(address, backlog)
  server.createContext("/", this)

  /**
   * Sends a HTTP response with the specified HTTP response code and string as content
   */
  def respond(exchange: HttpExchange)(response: Response) {
    val bytes = response.body.getBytes
    exchange.sendResponseHeaders(response.status, bytes.size)
    exchange.getResponseBody.write(bytes)
    exchange.getResponseBody.write("\r\n\r\n".getBytes)
    exchange.getResponseBody.close()
    exchange.close()
  }

  def start() = server.start()

  def stop(delay: Int = 1) = server.stop(delay)
}

