package dmm4s

/**
  * DMM.com 女優API のレスポンス.
  *
  * @param request リクエスト
  * @param result リクエソ結果
  */
final case class ActressSearchResponse(
    request: ActressSearchResponse.Request,
    result: ActressSearchResponse.Result
)

object ActressSearchResponse {

  implicit val actressSearchResponseCodecJson
      : CodecJson[ActressSearchResponse] =
    CodecJson.casecodec2(apply, unapply)("request", "result")

  /** DMM.com 女優検索 API のレスポンスに含まれるリクエスト情報.
    *
    * @param parameters リクエストパラメータ
    */
  final case class Request(parameters: InfoSearchParams)

  object Request {

    implicit val requestCodecJson: CodecJson[Request] =
      CodecJson.casecodec1(apply, unapply)("parameters")
  }

  /** DMM.com 女優検索 API のレスポンス結果.
    *
    * @param status ステータスコード
    * @param resultCount 取得件数
    * @param totalCount 全体件数
    * @param firstPosition 検索開始位置
    * @param actress 女優情報
    */
  final case class Result(
      status: Int,
      resultCount: Int,
      totalCount: Int,
      firstPosition: Int,
      actress: List[Actress]
  )

  object Result {
    implicit val resultCodecJson: CodecJson[Result] =
      CodecJson.casecodec5(apply, unapply)(
        "status",
        "result_count",
        "total_count",
        "first_position",
        "actress"
      )
  }

  /** DMM.com 女優検索API の女優情報.
    *
    * @param id 女優ID
    * @param name 女優名
    * @param ruby 女優名（読み仮名）
    * @param bust バスト
    * @param cup カップ数
    * @param wast ウェスト
    * @param hip ヒップ
    * @param height 身長
    * @param birthday 生年月日
    * @param bloodType 血液型
    * @param hobby 趣味
    * @param prefecture 出身地
    * @param imageUrl 画像URL
    * @param listUrl リストページURL（アフィリエイトID付き）
    */
  final case class Actress(
      id: Long,
      name: String,
      ruby: String,
      bust: String,
      cup: String,
      wast: String,
      hip: String,
      height: Option[String],
      birthday: Option[String],
      bloodType: Option[String],
      hobby: Option[String],
      prefecture: Option[String],
      imageUrl: Option[ImageUrl],
      listUrl: ListUrl
  )

  object Actress {

    implicit val actressCodecJson: CodecJson[Actress] =
      CodecJson.casecodec14(apply, unapply)(
        "id",
        "name",
        "ruby",
        "bust",
        "cup",
        "wast",
        "hip",
        "height",
        "birthday",
        "blood_type",
        "hobby",
        "prefecture",
        "imageURL",
        "listURL"
      )
  }

  /** DMM.com 女優API のレスポンス結果に含まれる画像URL.
    *
    * @param small 画像（小）
    * @param large 画像（大）
    */
  final case class ImageUrl(small: String, large: String)

  object ImageUrl {
    implicit val imageUrlCodecJson: CodecJson[ImageUrl] =
      CodecJson.casecodec2(apply, unapply)("small", "large")
  }

  /** DMM.com 女優API のレスポンスに含まれるリストページURL（アフィリエイトID付き）.
    *
    * @param digital 動画
    * @param monthlyPreminum 月額動画 見放題chプレミアム
    * @param ppm 10円動画
    * @param mono DVD通販
    * @param rent DVDレンタル
    */
  final case class ListUrl(
      digital: String,
      monthlyPreminum: String,
      ppm: String,
      mono: String,
      rent: String
  )

  object ListUrl {
    implicit val listUrlCodecJson: CodecJson[ListUrl] =
      CodecJson.casecodec5(apply, unapply)(
        "digital",
        "monthly_premium",
        "ppm",
        "mono",
        "rent"
      )
  }
}
