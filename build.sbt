import java.nio.charset.Charset

name := "example"

version := "0.1"

scalaVersion := "2.12.8"


enablePlugins(ScalaJSPlugin)
enablePlugins(ScalaJSBundlerPlugin)

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

resolvers += "Apollo Bintray" at "https://dl.bintray.com/apollographql/maven/"

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6"
libraryDependencies += "org.scala-lang.modules" %% "scala-async" % "0.9.6"


libraryDependencies += "com.apollographql" %%% "apollo-scalajs-core" % "0.5.0" // if you are writing a vanilla Scala.js app

npmDependencies in Compile += "apollo-boost" -> "0.1.16"
npmDependencies in Compile += "apollo-link" -> "1.2.6"
npmDependencies in Compile += "graphql-tag" -> "2.10.1"
npmDependencies in Compile += "graphql" -> "14.1.1"
npmDependencies in Compile += "node-fetch" -> "1.6.3"

val namespace = "com.apollographql.scalajs"

lazy val apolloCodeGen = settingKey[Task[Seq[File]]]("Apollo GraphQL code generation")

(sourceGenerators in Compile) += apolloCodeGen.taskValue

apolloCodeGen := Def.task {
  import scala.sys.process._

  val out = (sourceManaged in Compile).value
  out.mkdirs()

  val graphQLScala = out / "graphql.scala"

  val ps = Seq(
    "apollo", 
    "codegen:generate",
    graphQLScala.getAbsolutePath,
    s"--localSchemaFile=${(baseDirectory.value / "schema.json").getAbsolutePath}",
    s"--queries=${((sourceDirectory in Compile).value / "graphql").getAbsolutePath}/*.graphql",
    s"--namespace=$namespace",
    "--target=scala"
  )

  streams.value.log.info(ps.mkString("Executing:\n"," \\\n",""))
  Process(ps, baseDirectory.value).!

  Seq(graphQLScala)
}.taskValue