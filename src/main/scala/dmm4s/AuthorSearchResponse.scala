package dmm4s

/** DMM.com 作者検索API のレスポンス.
  *
  * @param request リクエスト情報
  * @param result リクエスト結果
  */
final case class AuthorSearchResponse(
    request: AuthorSearchResponse.Request,
    result: AuthorSearchResponse.Result
)

object AuthorSearchResponse {

  implicit val authorSearchResponseCodecJson: CodecJson[AuthorSearchResponse] =
    CodecJson.casecodec2(apply, unapply)("request", "result")

  /** DMM.com 作者検索API のレスポンスに含まれるリクエスト情報.
    *
    * @param parameters リクエストパラメータ
    */
  final case class Request(parameters: InfoSearchParams)

  object Request {

    implicit val requestCodecJson: CodecJson[Request] =
      CodecJson.casecodec1(apply, unapply)("parameters")
  }

  /** DMM.com 作者検索API のレスポンス結果.
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
    * @param author 作者情報
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
      author: List[Result.Author]
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
        "author"
      )

    /** DMM.com 作者検索API のレスポンスに含まれる作者情報.
      *
      * @param authorId 作者ID
      * @param name 作者名
      * @param ruby 作者名（読み仮名）
      * @param authorName 作者別名
      * @param listUrl リストページURL（アフィリエイトID付き）
      */
    final case class Author(
        authorId: String,
        name: String,
        ruby: String,
        authorName: String,
        listUrl: String
    )

    object Author {

      implicit val authorCodecJson: CodecJson[Author] =
        CodecJson.casecodec5(apply, unapply)(
          "author_id",
          "name",
          "ruby",
          "author_name",
          "list_url"
        )
    }
  }
}
