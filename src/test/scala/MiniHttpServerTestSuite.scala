import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.scalatest.{BeforeAndAfterAll, FunSuite}

class MiniHttpServerTestSuite extends FunSuite with BeforeAndAfterAll {

  // Define a test server that has a few pages we can test against
  val server: MiniHttpServer = ???

  // Start the server before we start testing
  override def beforeAll() {
    server.start()
  }

  // Cleanup
  override def afterAll() {
    server.stop()
  }

  def getPage(url: String) = {
    val httpClient = HttpClientBuilder.create().build()
    val httpGet = new HttpGet(url)
    val response = httpClient.execute(httpGet)
    val entity = response.getEntity
    val content = entity.getContent
    (response, scala.io.Source.fromInputStream(content).getLines().mkString("\n"))
  }

  test("Server can serve plain HTML at root") {
    val (response, result) = getPage("http://localhost:8000/")
    assert(response.getStatusLine.getStatusCode == 200)
    assert(result == "It works!")
  }

  test("Server can serve Json at /json") {
    val (response, result) = getPage("http://localhost:8000/json")
    assert(response.getStatusLine.getStatusCode == 200)
    assert(response.getHeaders("Content-type")(0).getValue == "application/json")
    assert(result == """{message:"Hello World!"}""")
  }

  test("Server can serve plain plain text at /foo") {
    val (response, result) = getPage("http://localhost:8000/foo")
    assert(response.getStatusLine.getStatusCode == 200)
    assert(result == "And here's foo.")
  }

  test("Server respond 404 at /unknown") {
    val (response, _) = getPage("http://localhost:8000/unknown")
    assert(response.getStatusLine.getStatusCode == 404)
  }

  test("Server respond 500 at /exception") {
    val (response, result) = getPage("http://localhost:8000/exception")
    assert(response.getStatusLine.getStatusCode == 500)
    assert(result == "error")
  }

}