  name := "blackjack"
  version := "1.0"
  scalaVersion := "2.10.6"

  // Scalaz dependency
  libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.1.0"

  // Scalike JDBC dependencies
  libraryDependencies ++= Seq(
    "org.scalikejdbc" %% "scalikejdbc"       % "2.3.4",
    "com.h2database"  %  "h2"                % "1.4.190",
    "ch.qos.logback"  %  "logback-classic"   % "1.1.3"
  )

  // Declare MySQL connector Dependency
  libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.12"

  // Spray framework
  resolvers += "io.spray" at "http://repo.spray.io"
  libraryDependencies += "io.spray" % "spray-can" % "1.3.1"
  libraryDependencies += "io.spray" % "spray-client" % "1.3.1"
  libraryDependencies += "io.spray" % "spray-routing" % "1.3.1"

  // Akka
  libraryDependencies +=
    "com.typesafe.akka" %% "akka-actor" % "2.3.2"

  // Argonaut (JSON library)
  libraryDependencies +=
    "io.argonaut" %% "argonaut" % "6.1"

  // Unit tests
  libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"
  libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
  resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

  // Bcrypt
  libraryDependencies += "com.github.t3hnar" % "scala-bcrypt_2.10" % "2.1"