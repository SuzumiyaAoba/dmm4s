package dmm4s

/** DMM.com メーカー検索API のレスポンス.
  *
  * @param request リクエスト情報
  * @param result リクエスト結果
  */
final case class MakerSearchResponse(
    request: MakerSearchResponse.Request,
    result: MakerSearchResponse.Result
)

object MakerSearchResponse {

  implicit val makerSearchResponseCodecJson: CodecJson[MakerSearchResponse] =
    CodecJson.casecodec2(apply, unapply)("request", "result")

  /** DMM.com メーカー検索API のレスポンスに含まれるリクエスト情報.
    *
    * @param parameters
    */
  final case class Request(parameters: InfoSearchParams)

  object Request {

    implicit val requestCodecJson: CodecJson[Request] =
      CodecJson.casecodec1(apply, unapply)("parameters")

  }

  /** DMM.com メーカー検索API のレスポンス結果.
    *
    * @param status ステータスコード
    * @param resultCount 取得件数
    * @param totalCount 全体件数
    * @param firstPosition 検索開始位置
    * @param siteName サイト名
    * @param siteCode サイトコード
    * @param serviceName サービス名
    * @param serviceCode サービスコード
    * @param floorId フロア名
    * @param floorName フロアID
    * @param floorCode フロアコード
    * @param maker メーカー情報
    */
  final case class Result(
      status: Int,
      resultCount: Int,
      totalCount: Int,
      firstPosition: Int,
      siteName: String,
      siteCode: String,
      serviceName: String,
      serviceCode: String,
      floorId: String,
      floorName: String,
      floorCode: String,
      maker: List[Result.Maker]
  )

  object Result {

    implicit val resultCodecJson: CodecJson[Result] =
      CodecJson.casecodec12(apply, unapply)(
        "status",
        "result_count",
        "total_count",
        "first_position",
        "site_name",
        "site_code",
        "service_name",
        "service_code",
        "floor_id",
        "floor_name",
        "floor_code",
        "maker"
      )

    /** DMM.com メーカー検索API に含まれるメーカー情報.
      *
      * @param makerId メーカーID
      * @param name メーカー名
      * @param ruby メーカー名（読み仮名）
      * @param listUrl リストページURL（アフィリエイトID付き）
      */
    final case class Maker(
        makerId: String,
        name: String,
        ruby: String,
        listUrl: String
    )

    object Maker {

      implicit val makerCodecJson: CodecJson[Maker] =
        CodecJson.casecodec4(apply, unapply)(
          "maker_id",
          "name",
          "ruby",
          "list_url"
        )
    }
  }
}
