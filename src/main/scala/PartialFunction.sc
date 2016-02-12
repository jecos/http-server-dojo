val f = new PartialFunction[Int, String] {
  override def isDefinedAt(x: Int): Boolean = x == 1

  override def apply(v1: Int): String = "one"
}

f(1)
f.isDefinedAt(1)
f.isDefinedAt(2)


val one: PartialFunction[Int, String] = {
  case 1 => "one"
}


(1 to 20).filter(n => n % 2 == 0).map(n => s"$n est un multiple de 2")

(1 to 20).collect { case n if n % 2 == 0 => s"$n est un multiple de 2" }
(1 to 20).collect {
  case n if n % 10 == 0 => s"$n est un multiple de 10"
  case n if n % 2 == 0 => s"$n est un multiple de 2"
}

val multBy10 : PartialFunction[Int, String] = {case n if n % 10 == 0 => s"$n est un multiple de 10"}
val multBy2 : PartialFunction[Int, String] = {case n if n % 2 == 0 => s"$n est un multiple de 2"}

val mult = multBy10 orElse multBy2

(1 to 20).collect(mult)


