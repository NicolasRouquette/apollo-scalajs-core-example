# Example of apollo-scalajs (core only)

## [Apollo CLI](https://www.apollographql.com/docs/scalajs/essentials/installation.html#Apollo-CLI)

The doc is out-of-date
- the command is not 'apollo:codegen' but 'codegen:generate'
- the generated file must be the argument after 'codegen:generate', not the last argument

It is surprisingly easy to get an error in code generation like this:

```
Error: EISDIR: illegal operation on a directory, read
```

Unfortunately, it is very difficult to diagnose where the error comes from
because there is no '--verbose' option in 'codegen:generate'.

## [Creating a client](https://www.apollographql.com/docs/scalajs/essentials/getting-started.html#Create-a-client)

The example is ill-formed because the `main` function must have an 'args' parameter.
Put together, it is:

```
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
```

This fails with an error in the call to `ApolloBoostClient(...)`:

```
Error:
fetch is not found globally and no fetcher passed, to fix pass a fetch for
your environment like https://www.npmjs.com/package/node-fetch.

For example:
import fetch from 'node-fetch';
import { createHttpLink } from 'apollo-link-http';

const link = createHttpLink({ uri: '/graphql', fetch: fetch });
...
```




