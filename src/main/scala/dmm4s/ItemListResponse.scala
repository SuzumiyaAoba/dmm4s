package dmm4s

import argonaut.Argonaut, Argonaut.{jObjectFields, jString}

/** DMM.com 商品検索API ver3 のレスポンス.
  *
  * @param request リクエスト情報
  * @param result リクエスト結果
  */
final case class ItemListResponse(
    request: ItemListResponse.Request,
    result: ItemListResponse.Result
)

object ItemListResponse {

  implicit val itemListResponseCodecJson: CodecJson[ItemListResponse] =
    CodecJson.casecodec2(apply, unapply)("request", "result")

  /** DMM.com 商品検索API ver3 のレスポンスに含まれるリクエスト情報.
    *
    * @param parameters リクエストパラメータ
    */
  final case class Request(parameters: ItemListParams)

  object Request {
    implicit val requestCodecJson: CodecJson[Request] =
      CodecJson.casecodec1(apply, unapply)("parameters")
  }

  /** DMM.com 商品検索API ver3 のレスポンス結果.
    *
    * @param status ステータスコード
    * @param resultCount 取得件数
    * @param totalCount 全体件数
    * @param firstPosition 検索開始位置
    * @param items 商品情報
    */
  final case class Result(
      status: Int,
      resultCount: Int,
      totalCount: Int,
      firstPosition: Int,
      items: List[Item]
  )

  object Result {

    implicit val resultCodecJson: CodecJson[Result] =
      CodecJson.casecodec5(apply, unapply)(
        "status",
        "result_count",
        "total_count",
        "first_position",
        "items"
      )
  }

  /** DMM.com 商品検索API ver3 に含れる商品情報.
    *
    * @param serviceCode サービスコード
    * @param serviceName サービス名
    * @param floorCode フロアコード
    * @param floorName フロア名
    * @param categoryName カテゴリ名
    * @param contentId 商品ID
    * @param productId 品番
    * @param title タイトル
    * @param volume 収録時間 or ページ数
    * @param number 巻数
    * @param review レビュー
    * @param url 商品ページURL
    * @param affiliateUrl アフィリエイトリンクURL
    * @param urlSp すまほ向ケ商品ページURL
    * @param affiliateUrlSp スマホ向けアフィリエイトリンクURL
    * @param imageUrl 画像URL
    * @param tachiyomi 立ち読み
    * @param sampleImageUrl サンプル画像URL
    * @param sampleMovieUrl サンプル動画URL
    * @param prices 価格
    * @param date 発売日、配信開始日、貸出開始日
    * @param itemInfo 商品詳細
    * @param janCode JANコード
    * @param makerProduct メーカー品番
    * @param isbn ISBN
    * @param stock 在庫状況
    * @param cdInfo CD情報
    * @param bandaiInfo 在庫状況
    */
  final case class Item(
      serviceCode: String,
      serviceName: String,
      floorCode: String,
      floorName: String,
      categoryName: String,
      contentId: String,
      productId: String,
      title: String,
      volume: Option[String],
      number: Option[String],
      review: Option[Review],
      url: String,
      affiliateUrl: String,
      urlSp: String,
      affiliateUrlSp: String,
      imageUrl: ImageUrl,
      tachiyomi: Option[Tachiyomi],
      sampleImageUrl: Option[SampleImageUrl],
      sampleMovieUrl: Option[SampleMovieUrl],
      prices: Prices,
      date: String,
      itemInfo: ItemInfo,
      janCode: Option[String],
      makerProduct: Option[String],
      isbn: Option[String],
      stock: Option[String],
      cdInfo: Option[CdInfo],
      bandaiInfo: Option[BandaiInfo]
  )

  object Item {

    implicit val itemCodecJson: CodecJson[Item] =
      CodecJson(
        item => jObjectFields(("service_code", jString(item.serviceCode))),
        c =>
          for {
            serviceCode <- (c --\ "service_code").as[String]
            serviceName <- (c --\ "service_name").as[String]
            floorCode <- (c --\ "floor_code").as[String]
            floorName <- (c --\ "floor_name").as[String]
            categoryName <- (c --\ "category_name").as[String]
            contentId <- (c --\ "content_id").as[String]
            productId <- (c --\ "product_id").as[String]
            title <- (c --\ "title").as[String]
            volume <- (c --\ "volume").as[Option[String]]
            number <- (c --\ "number").as[Option[String]]
            review <- (c --\ "review").as[Option[Review]]
            url <- (c --\ "URL").as[String]
            urlSp <- (c --\ "URLsp").as[String]
            affiliateUrlSp <- (c --\ "affiliateURLsp").as[String]
            affiliateUrl <- (c --\ "affiliateURL").as[String]
            imageUrl <- (c --\ "imageURL").as[ImageUrl]
            tachiyomi <- (c --\ "tachiyomi").as[Option[Tachiyomi]]
            sampleImageUrl <-
              (c --\ "sampleImageURL").as[Option[SampleImageUrl]]
            sampleMovieUrl <-
              (c --\ "sampleMovieURL").as[Option[SampleMovieUrl]]
            prices <- (c --\ "prices").as[Prices]
            date <- (c --\ "date").as[String]
            itemInfo <- (c --\ "iteminfo").as[ItemInfo]
            janCode <- (c --\ "jancode").as[Option[String]]
            makerProduct <- (c --\ "maker_product").as[Option[String]]
            isbn <- (c --\ "isbn").as[Option[String]]
            stock <- (c --\ "stock").as[Option[String]]
            cdInfo <- (c --\ "cdinfo").as[Option[CdInfo]]
            bandaiInfo <- (c --\ "bandaiInfo").as[Option[BandaiInfo]]
          } yield Item(
            serviceCode,
            serviceName,
            floorCode,
            floorName,
            categoryName,
            contentId,
            productId,
            title,
            volume,
            number,
            review,
            url,
            affiliateUrl,
            urlSp,
            affiliateUrlSp,
            imageUrl,
            tachiyomi,
            sampleImageUrl,
            sampleMovieUrl,
            prices,
            date,
            itemInfo,
            janCode,
            makerProduct,
            isbn,
            stock,
            cdInfo,
            bandaiInfo
          )
      )
  }

  /** DMM.com 商品情報API ver3 のレスポンスに含まれるレビュー.
    *
    * @param count レビュー数
    * @param average レビュー平均点
    */
  final case class Review(
      count: Int,
      average: Double
  )

  object Review {

    implicit val reviewCodecJson: CodecJson[Review] =
      CodecJson.casecodec2(apply, unapply)("count", "average")
  }

  /** DMM.com 商品情報API ver3 のレスポンスに含まれる画像URL.
    *
    * @param list リストページ用
    * @param small 末端用（小）
    * @param large　末端用（大）
    */
  final case class ImageUrl(
      list: String,
      small: String,
      large: String
  )

  object ImageUrl {

    implicit val imageUrlCodecJson: CodecJson[ImageUrl] =
      CodecJson.casecodec3(apply, unapply)("list", "small", "large")
  }

  /** DMM.com 商品情報API ver3 のレスポンスに含まれる立ち読みURL.
    *
    * @param url 立ち読みページURL
    * @param affiliateUrl 立ち読みアフィリエイトリンクURL
    */
  final case class Tachiyomi(
      url: String,
      affiliateUrl: String
  )

  object Tachiyomi {

    implicit val tachiyomiCodecJson: CodecJson[Tachiyomi] =
      CodecJson.casecodec2(apply, unapply)("URL", "affiliateURL")
  }

  /** DMM.com 商品情報API ver3 のレスポンスに含まれるサンプル画像URL.
    *
    * @param sampleS サンプル（小）リスト
    */
  final case class SampleImageUrl(
      sampleS: SampleImageUrl.SampleS
  )

  object SampleImageUrl {

    implicit val sampleImageUrlCodecJson: CodecJson[SampleImageUrl] =
      CodecJson.casecodec1(apply, unapply)("sample_s")

    /** DMM.com 商品情報API ver3 のレスポンスに含まれるサンプル（小）リスト.
      *
      * @param image サンプル画像（小）
      */
    final case class SampleS(
        image: List[String]
    )

    object SampleS {

      implicit val sampleSCodecJson: CodecJson[SampleS] =
        CodecJson.casecodec1(apply, unapply)("image")
    }
  }

  /** DMM.com 商品情報API ver3 のレスポンスに含まれるサンプル動画URL.
    *
    * @param size476x306 476x306
    * @param size560x360 560x360
    * @param size644x414 644x414
    * @param size720x480 720x480
    * @param pcFlag PC対応しているか
    * @param spFlag スマホ対応しているか
    */
  final case class SampleMovieUrl(
      size476x306: String,
      size560x360: String,
      size644x414: String,
      size720x480: String,
      pcFlag: Int,
      spFlag: Int
  )

  object SampleMovieUrl {

    implicit val sampleMovieUrlCodecJson: CodecJson[SampleMovieUrl] =
      CodecJson.casecodec6(apply, unapply)(
        "size_476_306",
        "size_560_360",
        "size_644_414",
        "size_720_480",
        "pc_flag",
        "sp_flag"
      )
  }

  /** DMM.com 商品情報 ver3 のレスポンスに含まれる価格.
    *
    * @param price 価格
    * @param listPrice 定価
    * @param deliveries 配信リスト
    */
  final case class Prices(
      price: String,
      listPrice: Option[String],
      deliveries: Prices.Deliveries
  )

  object Prices {

    implicit val pricesCodecJson: CodecJson[Prices] =
      CodecJson.casecodec3(apply, unapply)(
        "price",
        "list_price",
        "deliveries"
      )

    /** DMM.com 商品情報API のレスポンスに含まれる配信リスト.
      *
      * @param deliveries 配信リスト
      */
    final case class Deliveries(
        delivery: List[Deliveries.Delivery]
    )

    object Deliveries {

      implicit val deliveriesCodecJson: CodecJson[Deliveries] =
        CodecJson.casecodec1(apply, unapply)("delivery")

      /** DMM.com 商品情報API ver3 に含まれる配信.
        *
        * @param type 配信タイプ
        * @param price 配信価格
        */
      final case class Delivery(
          `type`: String,
          price: String
      )

      object Delivery {
        implicit val deliveryCodecJson: CodecJson[Delivery] =
          CodecJson.casecodec2(apply, unapply)("type", "price")
      }
    }
  }

  /** DMM.com 商品情報API ver3 のレスポンスに含まれる商品詳細.
    *
    * @param genre ジャンル
    * @param series シリーズ
    * @param maker メーカー
    * @param actor 出演者（一般作品のみ）
    * @param actress 女優（アダルト作品のみ）
    * @param director 監督
    * @param author 作家、原作者、著者
    * @param label レーベル
    */
  final case class ItemInfo(
      genre: Option[List[ItemInfo.Genre]],
      series: Option[List[ItemInfo.Series]],
      maker: Option[List[ItemInfo.Maker]],
      actor: Option[List[ItemInfo.Actor]],
      actress: Option[List[ItemInfo.Actress]],
      director: Option[List[ItemInfo.Director]],
      author: Option[List[ItemInfo.Author]],
      label: Option[List[ItemInfo.Label]]
  )

  object ItemInfo {

    implicit val itemInfoCodecJson: CodecJson[ItemInfo] =
      CodecJson.casecodec8(apply, unapply)(
        "genre",
        "series",
        "maker",
        "actor",
        "actress",
        "director",
        "author",
        "label"
      )

    /** DMM.com 商品情報API ver3 のレスポンスに含まれるジャンル.
      *
      * @param name ジャンル名
      * @param id ジャンルID
      */
    final case class Genre(
        name: String,
        id: Long
    )

    object Genre {

      implicit val genreCodecJson: CodecJson[Genre] =
        CodecJson.casecodec2(apply, unapply)("name", "id")
    }

    /** DMM.com 商品情報API ver3 のレスポンスに含まれるシリーズ.
      *
      * @param name シリーズ名
      * @param id シリーズID
      */
    final case class Series(
        name: String,
        id: Long
    )

    object Series {

      implicit val seriesCodecJson: CodecJson[Series] =
        CodecJson.casecodec2(apply, unapply)("name", "id")
    }

    /** DMM.com 商品情報API ver3 のレスポンスに含まれるメーカー.
      *
      * @param name メーカー名
      * @param id メーカーID
      */
    final case class Maker(
        name: String,
        id: Long
    )

    object Maker {

      implicit val makerCodecJson: CodecJson[Maker] =
        CodecJson.casecodec2(apply, unapply)("name", "id")
    }

    /** DMM.com 商品情報API ver3 のレスポンスに含まれる出演者（一般作品のみ）.
      *
      * @param name 出演者名
      * @param id 出演者ID
      */
    final case class Actor(
        name: String,
        id: Long
    )

    object Actor {

      implicit val actorCodecJson: CodecJson[Actor] =
        CodecJson.casecodec2(apply, unapply)("name", "id")
    }

    /** DMM.com 商品情報API ver3 のレスポンスに含まれる女優（アダルト作品のみ）.
      *
      * @param name 女優名
      * @param id 女優ID
      */
    final case class Actress(
        name: String,
        id: Long
    )

    object Actress {

      implicit val actressCodecJson: CodecJson[Actress] =
        CodecJson.casecodec2(apply, unapply)("name", "id")
    }

    /** DMM.com 商品情報API ver3 のレスポンスに含まれる監督.
      *
      * @param name 監督名
      * @param id 監督ID
      */
    final case class Director(
        name: String,
        id: Long
    )

    object Director {

      implicit val directorCodecJson: CodecJson[Director] =
        CodecJson.casecodec2(apply, unapply)("name", "id")
    }

    /** DMM.com 商品情報API ver3 のレスポンスに含まれる作家、原作者、著者
      *
      * @param name 作家、原作者、著者名
      * @param id 作家、原作者、著者ID
      */
    final case class Author(
        name: String,
        id: Long
    )

    object Author {

      implicit val authorCodecJson: CodecJson[Author] =
        CodecJson.casecodec2(apply, unapply)("name", "id")
    }

    /** DMM.com 商品情報API ver3 のレスポンスに含まれるレーベル
      *
      * @param name レーベル名
      * @param id レーベルID
      */
    final case class Label(
        name: String,
        id: Long
    )

    object Label {

      implicit val labelCodecJson: CodecJson[Label] =
        CodecJson.casecodec2(apply, unapply)("name", "id")
    }
  }

  /** DMM.com 商品情報API ver3 のレスポンスに含まれるCD情報.
    *
    * @param kind アルバム、シングル
    */
  final case class CdInfo(
      kind: String
  )

  object CdInfo {

    implicit val cdInfoCodecJson: CodecJson[CdInfo] =
      CodecJson.casecodec1(apply, unapply)("kind")
  }

  /** DMM.com 商品情報API ver3 のレスポンスに含まれるバンダイ情報.
    *
    * @param titleCode タイトルコード
    */
  final case class BandaiInfo(
      titleCode: String
  )

  object BandaiInfo {
    implicit val bandaiInfoCodecJson: CodecJson[BandaiInfo] =
      CodecJson.casecodec1(apply, unapply)("bandai_info")
  }
}
