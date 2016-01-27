import scalikejdbc._
import scalaz._

/**
  * Created by freezing on 1/27/16.
  */
object UserService extends App {

  // initialize JDBC driver & connection pool
  Class.forName("com.mysql.jdbc.Driver")
  ConnectionPool.singleton("jdbc:mysql://33.33.33.10:3306/bankstatementprofiler", "root", "root")

  // ad-hoc session provider on the REPL
  implicit val session = AutoSession

  val rawValue = "THis is a raw transaction"

  
  sql"INSERT INTO RawTransaction (RowValue) VALUES ($rawValue)".update().apply()
}
