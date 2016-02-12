
case class Response(status: Int, body: String)

case object NotFound extends Response(404, "")

case class Ok(override val body: String) extends Response(200, body)

case class InternalError(override val body: String) extends Response(500, body)
