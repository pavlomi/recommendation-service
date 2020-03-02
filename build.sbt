name := "recommendation"

organization := "pavlomi"

version := "0.0.1"

scalaVersion := "2.12.6"

resolvers += Resolver.typesafeRepo("releases")

scalacOptions += "-Ypartial-unification"

libraryDependencies ++= {
  val akkaV = "2.4.20"
  val akkaHttpV = "10.0.15"
  val scalaTestV = "3.0.1"
  val scalaMockV = "3.5.0"
  val enumeratumV = "1.5.12"
  val kebsV = "1.6.3"
  val bcryptV = "3.0"
  val catsV = "2.1.1"
  val lettuceV =  "5.2.2.RELEASE"

  lazy val akkaBase = Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.typesafe.akka" %% "akka-slf4j" % akkaV
  )

  lazy val akkaHttp = Seq(
    "com.typesafe.akka" %% "akka-http-core" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV % "test",
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV
  )

  lazy val others = Seq(
    "com.beachape" %% "enumeratum" % enumeratumV,
    "pl.iterators" %% "kebs-spray-json" % kebsV,
    "com.github.t3hnar" %% "scala-bcrypt" % bcryptV
  )

  lazy val cache = Seq(
    "io.lettuce" % "lettuce-core" %  lettuceV
  )

  lazy val cats = Seq(
    "org.typelevel" %% "cats-core" % catsV,
    "org.typelevel" %% "cats-free" % catsV,
    "org.typelevel" %% "cats-kernel" % catsV,
    "org.typelevel" %% "cats-macros" % catsV,
  )

  akkaBase ++ akkaHttp ++ cats ++ others ++ cache
}
