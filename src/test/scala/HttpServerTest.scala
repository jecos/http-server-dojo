class HttpServerTest {


  val server = MiniHttpServer {
    case e@p("/toto") => Ok("dewdw")

  }
}
