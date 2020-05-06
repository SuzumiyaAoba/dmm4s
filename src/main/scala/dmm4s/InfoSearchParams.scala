package dmm4s

/** ジャンル検索API、メーカー検索API、作者検索APIのリクエストパラメータ.
  *
  * @param apiId API ID
  * @param affiliateId アフィリエイトID
  * @param floorId フロアID
  * @param initial 頭文字（50音）
  * @param hits 取得件数（デフォルト: 20）
  * @param offset 検索開始位置（デフォルト: 1）
  * @param output 出力形式
  * @param callback コールバック
  */
final case class InfoSearchParams(
    apiId: String,
    affiliateId: String,
    floorId: Option[String] = None,
    initial: Option[Char] = None,
    hits: Option[Int] = None,
    offset: Option[Int] = None,
    output: Option[String] = None,
    callback: Option[String] = None
)

object InfoSearchParams {

  implicit val infoSearchParamsCodecJson: CodecJson[InfoSearchParams] =
    CodecJson.casecodec8(apply, unapply)(
      "api_id",
      "affiliate_id",
      "floor_id",
      "intial",
      "hits",
      "offset",
      "output",
      "callback"
    )
}
