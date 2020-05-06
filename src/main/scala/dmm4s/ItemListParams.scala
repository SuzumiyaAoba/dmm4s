package dmm4s

import argonaut.Argonaut, Argonaut.{jString}
import scalaz.syntax.std.option._

/** DMM.com 商品検索API ver3 リクエストパラメータ
  *
  * @param apiId API ID
  * @param affiliateId アフィリエイトID
  * @param site サイトID
  * @param service サービス
  * @param floor フロア
  * @param hits 取得件数
  * @param offset 検索開始位置
  * @param sort ソート順
  * @param keyword キーワード
  * @param cid 商品ID
  * @param article 絞りこみ項目
  * @param articleId 絞り込みID
  * @param gteDate 発売日絞り込み（始まり）
  * @param lteDate 発売日絞り込み（終わり）
  * @param monoStock 在庫絞り込み
  * @param output 出力形式
  * @param callback コールバック
  */
final case class ItemListParams(
    apiId: String,
    affiliateId: String,
    site: ItemListParams.Site,
    service: Option[String] = None,
    floor: Option[String] = None,
    hits: Option[Int] = None,
    offset: Option[Int] = None,
    sort: Option[ItemListParams.Sort] = None,
    keyword: Option[String] = None,
    cid: Option[String] = None,
    article: Option[ItemListParams.Article] = None,
    articleId: Option[Long] = None,
    gteDate: Option[DateTime] = None,
    lteDate: Option[DateTime] = None,
    monoStock: Option[ItemListParams.MonoStock] = None,
    output: Option[String] = None,
    callback: Option[String] = None
)

object ItemListParams {

  implicit val itemListRequestCodecJson: CodecJson[ItemListParams] =
    CodecJson.casecodec17(apply, unapply)(
      "api_id",
      "affiliate_id",
      "site",
      "service",
      "floor",
      "hits",
      "offset",
      "sort",
      "keyword",
      "cid",
      "article",
      "article_id",
      "gte_date",
      "lte_date",
      "mono_stock",
      "output",
      "callback"
    )

  /** DMM.com 商品検索API ver3 に含まれるサイト */
  sealed trait Site {

    import Site._

    def value: String =
      this match {
        case Dmm   => "DMM.com"
        case Fanza => "FANZA"
      }
  }

  object Site {

    implicit val siteCodecJson: CodecJson[Site] =
      CodecJson(
        site => jString(site.value),
        c => c.as[String].map(this.fromString).map(_.getOrElse(null))
      )

    def fromString(str: String): Option[Site] = {
      str match {
        case "DMM.com" => Dmm.some
        case "FANZA"   => Fanza.some
        case _         => None
      }
    }

    /** DMM.com */
    case object Dmm extends Site

    /** FANZA */
    case object Fanza extends Site
  }

  /** ソート順（DMM.com 商品検索API ver3） */
  sealed trait Sort {

    import Sort._

    /** ソート順を値へ変換する */
    def value: String =
      this match {
        case RankDesc   => "rank"
        case PriceDesc  => "price"
        case PriceAsc   => "-price"
        case DateDesc   => "date"
        case ReviewDesc => "review"
        case MatchDesc  => "match"
      }
  }

  object Sort {

    implicit val sortCodecJson: CodecJson[Sort] =
      CodecJson(
        sort => jString(sort.value),
        c => c.as[String].map(this.fromString(_).getOrElse(null))
      )

    def fromString(str: String): Option[Sort] =
      str match {
        case "rank"   => RankDesc.some
        case "price"  => PriceDesc.some
        case "-price" => PriceAsc.some
        case "date"   => DateDesc.some
        case "review" => ReviewDesc.some
        case "match"  => MatchDesc.some
        case _        => None
      }

    /** 人気 */
    case object RankDesc extends Sort

    /** 価格が高い */
    case object PriceDesc extends Sort

    /** 価格低い */
    case object PriceAsc extends Sort

    /** 新着 */
    case object DateDesc extends Sort

    /** 評価 */
    case object ReviewDesc extends Sort

    /** マッチング */
    case object MatchDesc extends Sort
  }

  /** 絞りこみ項目（DMM.com 商品検索API ver3） */
  sealed trait Article {

    import Article._

    /** 絞りこみ項目を値へ変換. */
    def value: String =
      this match {
        case Actress => "actress"
        case Author  => "author"
        case Genre   => "genre"
        case Series  => "series"
        case Maker   => "maker"
      }
  }

  object Article {

    implicit val articleCodecJson: CodecJson[Article] =
      CodecJson(
        article => jString(article.value),
        c =>
          c.as[String]
            .map(this.fromString)
            .map(_.getOrElse(null))
      )

    def fromString(str: String): Option[Article] =
      str match {
        case "actress" => Some(Actress)
        case "author"  => Some(Author)
        case "genre"   => Some(Genre)
        case "series"  => Some(Series)
        case "maker"   => Some(Maker)
        case _         => None
      }

    /** 女優 */
    case object Actress extends Article

    /** 作者 */
    case object Author extends Article

    /** ジャンル */
    case object Genre extends Article

    /** シリーズ */
    case object Series extends Article

    /** メーカー */
    case object Maker extends Article
  }

  /** 在庫絞り込み（DMM.com 商品検索API ver3） */
  sealed trait MonoStock {

    import MonoStock._

    /** 在庫絞り込みを値へ変換 */
    def value: String =
      this match {
        case NoSelection => ""
        case Stock       => "stock"
        case Reserve     => "reserve"
        case Mono        => "mono"
        case Dmp         => "dmp"
      }
  }

  object MonoStock {

    implicit val monoStockCodecJson: CodecJson[MonoStock] =
      CodecJson(
        monoStock => jString(monoStock.value),
        c =>
          c.as[String]
            .map(this.fromString)
            .map(_.getOrElse(null))
      )

    def fromString(str: String): Option[MonoStock] =
      str match {
        case ""        => Some(NoSelection)
        case "stock"   => Some(Stock)
        case "reserve" => Some(Reserve)
        case "mono"    => Some(Mono)
        case "dmp"     => Some(Dmp)
        case _         => None
      }

    /** 絞り込みなし */
    case object NoSelection extends MonoStock

    /** 在庫なし */
    case object Stock extends MonoStock

    /** 予約受付中 */
    case object Reserve extends MonoStock

    /** DMM通販のみ */
    case object Mono extends MonoStock

    /** マーケットプレイスのみ */
    case object Dmp extends MonoStock
  }

}
