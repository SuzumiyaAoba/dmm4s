package dmm4s

/** DMM.com フロアAPI のレスポンス.
  *
  * @param request リクエスト情報
  * @param result リクエスト結果
  */
final case class FloorListResponse(
    request: FloorListResponse.Request,
    result: FloorListResponse.Result
)

object FloorListResponse {

  implicit val floorListResponseCodecJson: CodecJson[FloorListResponse] =
    CodecJson.casecodec2(apply, unapply)("request", "response")

  /** DMM.com フロアAPI のレスポンスに含まれるリクエスト情報.
    *
    * @param parameters リクエストパラメータ
    */
  final case class Request(parameters: FloorListParams)

  object Request {

    implicit val requestCodecJson: CodecJson[Request] =
      CodecJson.casecodec1(apply, unapply)("parameters")
  }

  /** DMM.com フロアAPI のレスポンスのレスポンス結果.
    *
    * @param site サイト情報
    */
  final case class Result(site: List[Site])

  object Result {
    implicit val resultCodecJson: CodecJson[Result] =
      CodecJson.casecodec1(apply, unapply)("site")
  }

  /** DMM.com フロアAPI のレスポンスに含まれるサイト情報.
    *
    * @param name サイト名
    * @param code サイトコード
    * @param service サービス情報
    */
  final case class Site(
      name: String,
      code: String,
      service: List[Site.Service]
  )

  object Site {

    implicit val siteJsonCodec: CodecJson[Site] =
      CodecJson.casecodec3(apply, unapply)("name", "code", "service")

    /** DMM.com フロアAPI のレスポンスに含まれるサービス情報.
      *
      * @param name サービス名
      * @param code サービスコード
      * @param floor フロア情報
      */
    final case class Service(
        name: String,
        code: String,
        floor: Service.Floor
    )

    object Service {

      implicit val ServiceCodecJson: CodecJson[Service] =
        CodecJson.casecodec3(apply, unapply)("name", "code", "floor")

      /** DMM.com フロアAPI のレスポンスに含まれるフロア情報.
        *
        * @param id フロアID
        * @param name フロア名
        * @param code フロアコード
        */
      case class Floor(
          id: String,
          name: String,
          code: String
      )

      object Floor {
        implicit val floorCodecJson: CodecJson[Floor] =
          CodecJson.casecodec3(apply, unapply)("id", "name", "code")
      }
    }
  }
}
