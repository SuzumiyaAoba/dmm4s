package dmm4s

import argonaut.DecodeJson
import httpz._
import scalaz.{Free, Inject, NonEmptyList}
import scalaz.syntax.std.option._
import scala.annotation.meta.param

/**
  * コマンド抽象クラス.
  *
  * @param f ベースURLを受け取ってリクストを返すクラス
  * @param decoder JSONデコーダ
  */
sealed abstract class Command[A](val f: String => Request)(implicit
    val decoder: DecodeJson[A]
) {

  final def request: httpz.Request =
    requestWithURL(Dmm.baseURL)

  final def requestWithURL(baseURL: String): httpz.Request =
    f(baseURL)

  final def actionWithURL(baseURL: String): httpz.Action[A] =
    Core.json[A](requestWithURL(baseURL))(decoder)

  final def action: httpz.Action[A] =
    actionWithURL(Dmm.baseURL)

  final def lift[F[_]](implicit I: Inject[Command, F]): Free[F, A] =
    Free.liftF(I.inj(this))

  final def actionEOps: ActionEOps[httpz.Error, A] =
    new ActionEOps(action)

  final def nel: ActionE[NonEmptyList[httpz.Error], A] =
    actionEOps.nel
}

object Command {

  private[dmm4s] def get(
      url: String,
      opt: Config = httpz.emptyConfig
  ): String => Request =
    baseURL => opt(Request(url = baseURL + url))

  /** DMM.com 商品検索API ver3 コマンド.
    *
    * @param params リクエストパラメータ
    */
  final case class ItemList(params: ItemListParams)
      extends Command[ItemListResponse](
        get(
          "ItemList",
          Request.paramsOpt(
            ("api_id", params.apiId.some),
            ("affiliate_id", params.affiliateId.some),
            ("site", params.site.value.some),
            ("service", params.service),
            ("floor", params.floor),
            ("hits", params.hits.map(_.toString)),
            ("offset", params.offset.map(_.toString)),
            ("sort", params.sort.map(_.value)),
            ("keyword", params.keyword),
            ("cid", params.cid),
            ("article", params.article.map(_.value)),
            ("article_id", params.articleId.map(_.toString)),
            ("gte_date", params.gteDate.map(_.toString)),
            ("lte_date", params.lteDate.map(_.toString)),
            ("mono_stock", params.monoStock.map(_.value)),
            ("output", params.output.map(_.toString())),
            ("callback", params.callback.map(_.toString))
          )
        )
      )

  /** DMM.com フロアAPI コマンド.
    *
    * @param params リクエストパラメータ
    */
  final case class FloorList(params: FloorListParams)
      extends Command[FloorListResponse](
        get(
          "FloorList",
          Request.paramsOpt(
            ("api_id", params.apiId.some),
            ("affiliate_id", params.affiliateId.some),
            ("output", params.output),
            ("callback", params.callback)
          )
        )
      )

  /** DMM.com 女優検索API コマンド.
    *
    * @param params リクエストパラメータ
    */
  final case class ActressSearch(params: ActressSearchParams)
      extends Command[ActressSearchResponse](
        get(
          "ActressSearch",
          Request.paramsOpt(
            ("api_id", params.apiId.some),
            ("affiliate_id", params.affiliateId.some),
            ("site", params.initial.map(_.toString)),
            ("actress_id", params.actressId),
            ("keyword", params.keyword),
            ("gte_bust", params.gteBust.map(_.toString)),
            ("lte_bust", params.lteBust.map(_.toString)),
            ("gte_waist", params.gteWaist.map(_.toString)),
            ("lte_waist", params.lteWaist.map(_.toString)),
            ("gte_hip", params.gteHip.map(_.toString)),
            ("lte_hip", params.lteHip.map(_.toString)),
            ("gte_height", params.gteHeight.map(_.toString)),
            ("lte_height", params.lteHeight.map(_.toString)),
            ("gte_birthday", params.gteBirthday.map(_.toString)),
            ("lte_birthday", params.lteBirthday.map(_.toString)),
            ("hits", params.hits.map(_.toString)),
            ("offset", params.offset.map(_.toString)),
            ("sort", params.sort.map(_.value)),
            ("output", params.output),
            ("callback", params.callback)
          )
        )
      )

  private def infoSearchConfig(params: InfoSearchParams): Config =
    Request.paramsOpt(
      ("api_id", Some(params.apiId)),
      ("affiliate_id", Some(params.affiliateId)),
      ("floor_id", params.floorId),
      ("initial", params.initial.map(_.toString)),
      ("hits", params.hits.map(_.toString)),
      ("offset", params.offset.map(_.toString)),
      ("output", params.output.map(_.toString)),
      ("callback", params.callback)
    )

  /** DMM.com ジャンル検索API コマンド.
    *
    * @param params リクエストパラメータ
    */
  final case class GenreSearch(params: InfoSearchParams)
      extends Command[GenreSearchResponse](
        get("GenreSearch", infoSearchConfig(params))
      )

  /** DMM.com メーカー検索API コマンド.
    *
    * @param params リクエストパラメータ
    */
  final case class MakerSearch(params: InfoSearchParams)
      extends Command[MakerSearchResponse](
        get("MakerSearch", infoSearchConfig(params))
      )

  /** DMM.com シリーズ検索API コマンド.
    *
    * @param params リクエストパラメータ
    */
  final case class SeriesSearch(params: InfoSearchParams)
      extends Command[SeriesSearchResponse](
        get("InfoSearch", infoSearchConfig(params))
      )

  /** DMM.com シリーズ検索API コマンド.
    *
    * @param params リクエストパラメータ
    */
  final case class AuthorSearch(params: InfoSearchParams)
      extends Command[AuthorSearchResponse](
        get("AuthorSearch", infoSearchConfig(params))
      )
}
