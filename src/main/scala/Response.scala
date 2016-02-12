
case class Response(status: Int, body: String)

object NotFound {
  def apply(): Response = Response(404, "")

  def unapply(r: Response): Boolean = r.status == 404

}

object Ok {
  def apply(body: String): Response = Response(200, body)

  def unapply(r: Response): Boolean = r.status == 200

}

object InternalError {
  def apply(body: String): Response = Response(500, body)

  def unapply(r: Response): Boolean = r.status == 500
}


