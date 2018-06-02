package app.shared.data.views.v2_toRemove

object Utils{
  def mapLeft[A,B,C](e:Either[A,B], f: A=>C) : Either[C,B]= {
    val res = e match {
      case Left(a)  => Left(f(a))
      case Right(b) => Right(b)
    }
    res
  }

}
