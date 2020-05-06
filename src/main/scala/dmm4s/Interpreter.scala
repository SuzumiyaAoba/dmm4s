package dmm4s

import httpz.Action
import scalaz.~>

private[dmm4s] object Interpreter extends (Command ~> Action) {

  override def apply[A](fa: Command[A]) = fa.action
}
