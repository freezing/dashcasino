  name := "blackjack"
  version := "1.0"
  scalaVersion := "2.11.7"

  // Scalaz dependency
  libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.0"

  // Scalike JDBC dependencies
  libraryDependencies ++= Seq(
    "org.scalikejdbc" %% "scalikejdbc"       % "2.3.4",
    "com.h2database"  %  "h2"                % "1.4.190",
    "ch.qos.logback"  %  "logback-classic"   % "1.1.3"
  )

  // Declare MySQL connector Dependency
  libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.12"