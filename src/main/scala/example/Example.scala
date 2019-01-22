package example
import com.apollographql.scalajs._
import scala.scalajs.js

import scala.concurrent.ExecutionContext.Implicits.global

object Example {

  def main(args: Array[String]): Unit = { // called when the app launches
    val client = ApolloBoostClient(
      uri = "https://graphql-currency-rates.glitch.me"
    )
    client.query[js.Object](gql( // gql is a member of the com.apollographql.scalajs package that parses your query
      """{
        |  rates(currency: "USD") {
        |    currency
        |  }
        |}""".stripMargin
    )).foreach { result =>
      println(result.data)
    }
  }
}
