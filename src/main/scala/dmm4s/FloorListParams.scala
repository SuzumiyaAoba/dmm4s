package dmm4s

/** DMM.com フロアAPI のリクエストパラメータ.
  *
  * @param apiId API ID
  * @param affiliateId アフィリエイトID
  * @param output 出力形式
  * @param callback コールバック
  */
final case class FloorListParams(
    apiId: String,
    affiliateId: String,
    output: Option[String],
    callback: Option[String]
)

object FloorListParams {

  implicit val floorListParamsCodecJson: CodecJson[FloorListParams] =
    CodecJson.casecodec4(apply, unapply)(
      "api_id",
      "affiliate_id",
      "output",
      "callback"
    )
}
