package dmm4s

import scala.io.Codec

/** DMM.com シリーズ検索API のレスポンス.
  *
  * @param request リクエスト情報
  * @param result リクエスト結果
  */
final case class SeriesSearchResponse(
    request: SeriesSearchResponse.Request,
    result: SeriesSearchResponse.Result
)

object SeriesSearchResponse {

  implicit val seriesSearchResponseCodecJson: CodecJson[SeriesSearchResponse] =
    CodecJson.casecodec2(apply, unapply)("request", "result")

  final case class Request(parameters: InfoSearchParams)

  object Request {
    implicit val requestCodecJson: CodecJson[Request] =
      CodecJson.casecodec1(apply, unapply)("parameters")
  }

  /** DMM.com シリーズ検索API のレスポンス結果.
    *
    * @param status ステータスコード
    * @param resultCount 取得件数
    * @param totalCount 全体件数
    * @param firstPosition 検索開始位置
    * @param siteName サイト名
    * @param siteCode サイトコード
    * @param serviceName サービス名
    * @param serviceCode サービスコード
    * @param floorId フロアID
    * @param floorName フロア名
    * @param floorCode フロアコード
    * @param series シリーズ情報
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
      series: List[Result.Series]
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
        "series"
      )

    /** DMM.com シリーズ検索API のレスポンスに含まれるシリーズ情報.
      *
      * @param seriesId シリーズID
      * @param name シリーズ名
      * @param ruby シリーズ名（読み仮名）
      * @param listUrl リストページURL（アフィリエイトID付き）
      */
    final case class Series(
        seriesId: String,
        name: String,
        ruby: String,
        listUrl: String
    )

    object Series {

      implicit val seriesCodecJson: CodecJson[Series] =
        CodecJson.casecodec4(apply, unapply)(
          "series_id",
          "name",
          "ruby",
          "list_url"
        )
    }
  }
}
