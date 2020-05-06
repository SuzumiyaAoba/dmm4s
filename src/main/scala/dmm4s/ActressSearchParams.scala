package dmm4s

import argonaut.Argonaut, Argonaut.{jString}
import scalaz.syntax.std.option._

/** DMM.com 女優検索API のリクエストパラメータ.
  *
  * @param apiId API ID
  * @param affiliateId アフィリエイトID
  * @param initial 頭文字（50音）
  * @param actressId 女優ID
  * @param keyword キーワード
  * @param gteBust バスト下限
  * @param lteBust バスト上限
  * @param gteWaist バスト下限
  * @param lteWaist バスト上限
  * @param gteHip ヒップ下限
  * @param lteHip ヒップ上限
  * @param gteHeight 身長下限
  * @param lteHeight 身長上限
  * @param gteBirthday 誕生日下限
  * @param lteBirthday 誕生日上限
  * @param hits 取得件数
  * @param offset 検索開始位置
  * @param sort ソート順
  * @param output 出力形式
  * @param callback コールバック
  */
final case class ActressSearchParams(
    apiId: String,
    affiliateId: String,
    initial: Option[Char],
    actressId: Option[String],
    keyword: Option[String],
    gteBust: Option[Int],
    lteBust: Option[Int],
    gteWaist: Option[Int],
    lteWaist: Option[Int],
    gteHip: Option[Int],
    lteHip: Option[Int],
    gteHeight: Option[Int],
    lteHeight: Option[Int],
    gteBirthday: Option[LocalDate],
    lteBirthday: Option[LocalDate],
    hits: Option[Int],
    offset: Option[Int],
    sort: Option[ActressSearchParams.Sort],
    output: Option[String],
    callback: Option[String]
)

object ActressSearchParams {

  implicit val actressSearchParamsCodecJson: CodecJson[ActressSearchParams] =
    CodecJson.casecodec20(apply, unapply)(
      "api_id",
      "affiliate_id",
      "initial",
      "actress_id",
      "keyword",
      "gte_bust",
      "let_bust",
      "gte_waist",
      "lte_waist",
      "gte_hip",
      "lte_hip",
      "gte_height",
      "lte_height",
      "gte_birthday",
      "lte_birthday",
      "hits",
      "offset",
      "sort",
      "output",
      "callback"
    )

  sealed trait Sort {

    import Sort._

    def value: String =
      this match {
        case NameAsc       => "name"
        case NameDesc      => "-name"
        case BustAsc       => "bust"
        case BustDesc      => "-bust"
        case WaistAsc      => "waist"
        case WaistDesc     => "-waist"
        case HipAsc        => "hip"
        case HipDesc       => "-hip"
        case HeightAsc     => "height"
        case HeightDesc    => "-height"
        case BirthdayAsc   => "birthday"
        case BirthdayDesc  => "-birthday"
        case ActressIdAsc  => "id"
        case ActressIdDesc => "-id"
      }
  }

  object Sort {

    implicit val sortCodecJson: CodecJson[Sort] =
      CodecJson(
        sort => jString(sort.value),
        c => c.as[String].map(this.fromString).map(_.getOrElse(null))
      )

    def fromString(str: String): Option[Sort] =
      str match {
        case "name"      => NameAsc.some
        case "-name"     => NameDesc.some
        case "bust"      => BustAsc.some
        case "-bust"     => BustDesc.some
        case "waist"     => WaistAsc.some
        case "-waist"    => WaistDesc.some
        case "hip"       => HipAsc.some
        case "-hip"      => HipDesc.some
        case "height"    => HeightAsc.some
        case "-height"   => HeightDesc.some
        case "birthday"  => BirthdayAsc.some
        case "-birthday" => BirthdayDesc.some
        case "id"        => ActressIdAsc.some
        case "-id"       => ActressIdDesc.some
        case _           => None
      }

    /** 名前昇順 */
    case object NameAsc extends Sort

    /** 名前降順 */
    case object NameDesc extends Sort

    /** バスト昇順 */
    case object BustAsc extends Sort

    /** バスト降順 */
    case object BustDesc extends Sort

    /** ウエスト昇順 */
    case object WaistAsc extends Sort

    /** ウエスト降順 */
    case object WaistDesc extends Sort

    /** ヒップ昇順 */
    case object HipAsc extends Sort

    /** ヒップ降順 */
    case object HipDesc extends Sort

    /** 身長昇順 */
    case object HeightAsc extends Sort

    /** 身長降順 */
    case object HeightDesc extends Sort

    /** 生年月日昇順 */
    case object BirthdayAsc extends Sort

    /** 生年月日降順 */
    case object BirthdayDesc extends Sort

    /** 女優ID昇順 */
    case object ActressIdAsc extends Sort

    /** 女優ID降順 */
    case object ActressIdDesc extends Sort
  }
}
