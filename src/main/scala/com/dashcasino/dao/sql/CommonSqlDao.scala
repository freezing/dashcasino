package com.dashcasino.dao.sql

import scalikejdbc.AutoSession

/**
  * Created by freezing on 1/28/16.
  *
  * Block transactions are implemented in this class. Each method should be a transaction, i.e. guarantee atomicity.
  */
class CommonSqlDao(implicit val session: AutoSession.type) {

}
