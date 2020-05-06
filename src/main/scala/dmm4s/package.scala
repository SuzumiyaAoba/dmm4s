import argonaut.{EncodeJson, DecodeJson}
import java.text.SimpleDateFormat
import httpz.Action
import scalaz.~>

package object dmm4s {

  type DateTime = org.joda.time.DateTime

  type LocalDate = org.joda.time.LocalDate

  private[dmm4s] type JsonToString[A <: httpz.JsonToString[A]] =
    httpz.JsonToString[A]

  type CodecJson[A] = argonaut.CodecJson[A]
  val CodecJson = argonaut.CodecJson

  implicit val datetimeCodecJson: CodecJson[DateTime] =
    CodecJson.derived(
      EncodeJson.jencode1(_.toString()),
      DecodeJson.optionDecoder(
        {
          _.string.map { str =>
            new DateTime(
              (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")).parse(str)
            )
          }
        },
        "DateTime"
      )
    )

  implicit val dateCodecJson: CodecJson[LocalDate] =
    CodecJson.derived(
      EncodeJson.jencode1(_.toString),
      DecodeJson.optionDecoder(
        {
          _.string.map { str =>
            new LocalDate((new SimpleDateFormat("yyyy-MM-dd")).parse(str))
          }
        },
        "LocalDateTime"
      )
    )

  val interpreter: Command ~> Action = Interpreter
}
