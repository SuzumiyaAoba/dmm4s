package dmm4s

/** DMM.com ジャンル検索API のレスポンス.
  *
  * @param request リクエスト情報
  * @param response リクエスト結果
  */
final case class GenreSearchResponse(
    request: GenreSearchResponse.Request,
    response: GenreSearchResponse.Result
)

object GenreSearchResponse {

  implicit val genreSearchResponseCodecJson: CodecJson[GenreSearchResponse] =
    CodecJson.casecodec2(apply, unapply)("request", "response")

  /** DMM.com ジャンル検索API のレスポンスに含まれるリクエスト情報.
    *
    * @param parameters リクエストパラメータ
    */
  final case class Request(parameters: InfoSearchParams)

  object Request {

    implicit val requestCodecJson: CodecJson[Request] =
      CodecJson.casecodec1(apply, unapply)("parameters")
  }

  /** DMM.com ジャンル検索API のレスポンス結果.
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
    * @param genre ジャンル情報
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
      genre: List[Result.Genre]
  )

  object Result {

    implicit val resultCodecJson: CodecJson[Result] =
      CodecJson.casecodec11(apply, unapply)(
        "status",
        "result_code",
        "total_count",
        "first_position",
        "site_name",
        "site_code",
        "service_name",
        "service_code",
        "floor_id",
        "floor_name",
        "genre"
      )

    /** DMM.com ジャンル検索API のレスポンスに含まれるジャンル情報.
      *
      * @param genreId ジャンルID
      * @param name ジャンル名
      * @param ruby ジャンル名（読み仮名）
      * @param listUrl リストページURL（アフィリエイトID付き）
      */
    final case class Genre(
        genreId: String,
        name: String,
        ruby: String,
        listUrl: String
    )

    object Genre {

      implicit val genreCodecJson: CodecJson[Genre] =
        CodecJson.casecodec4(apply, unapply)(
          "genere_id",
          "name",
          "ruby",
          "list_url"
        )

    }
  }
}
