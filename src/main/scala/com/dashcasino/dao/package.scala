package com.dashcasino

import scalikejdbc.{LoggingSQLAndTimeSettings, GlobalSettings, AutoSession, ConnectionPool}

/**
  * Created by freezing on 1/28/16.
  */
package object dao {
  class DaoStatusCode extends Enumeration {
    val OK = Value(0)
    val ERROR = Value(1)
  }
  object DaoStatusCode extends DaoStatusCode
  case class ResultCode(status: DaoStatusCode.Value, description: String)
}
