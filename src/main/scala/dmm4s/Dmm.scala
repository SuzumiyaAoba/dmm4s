package dmm4s

import httpz._
import scalaz.{Inject, Free}
import dmm4s.Command.GenreSearch

object Dmm4s extends Dmm[Command, ({ type l[a] = Free[Command, a] })#l] {
  override protected[this] def f[A](c: Command[A]) = lift(c)
}

object Dmm extends Dmm[Command, Action] {

  implicit def instance[F[_]](implicit
      I: Inject[Command, F]
  ): Dmm[F, ({ type l[a] = Free[F, a] })#l] =
    new Dmm[F, (({ type l[a] = Free[F, a] })#l)] {
      def f[A](c: Command[A]) = lift(c)
    }

  def commands2Action[A](a: Free[Command, A]): Action[A] =
    a.foldMap(Interpreter)(httpz.ActionMonad)

  protected[this] override def f[A](c: Command[A]) =
    commands2Action(lift(c))

  /** ベースURL */
  private[dmm4s] final val baseURL = "https://api.dmm.com/affiliate/v3/"
}

sealed abstract class Dmm[F[_], G[_]](implicit I: Inject[Command, F]) {

  final type FreeF[A] = Free[F, A]

  final def lift[A](f: Command[A]): FreeF[A] =
    Free.liftF(I.inj(f))

  protected[this] def f[A](c: Command[A]): G[A]

  /** [[https://affiliate.dmm.com/api/v3/itemlist.html]] */
  def itemList(params: ItemListParams): G[ItemListResponse] =
    f(Command.ItemList(params))

  /** [[https://affiliate.dmm.com/api/v3/floorlist.html]] */
  def floorList(params: FloorListParams): G[FloorListResponse] =
    f(Command.FloorList(params))

  /** [[https://affiliate.dmm.com/api/v3/actresssearch.html]] */
  def actressSearch(params: ActressSearchParams): G[ActressSearchResponse] =
    f(Command.ActressSearch(params))

  /** [[https://affiliate.dmm.com/api/v3/genresearch.html]] */
  def genreSearch(params: InfoSearchParams): G[GenreSearchResponse] =
    f(Command.GenreSearch(params))

  /** [[https://affiliate.dmm.com/api/v3/seriessearch.html]] */
  def seriesSearch(params: InfoSearchParams): G[SeriesSearchResponse] =
    f(Command.SeriesSearch(params))

  /** [[https://affiliate.dmm.com/api/v3/authorsearch.html]] */
  def authorSearch(params: InfoSearchParams): G[AuthorSearchResponse] =
    f(Command.AuthorSearch(params))
}
