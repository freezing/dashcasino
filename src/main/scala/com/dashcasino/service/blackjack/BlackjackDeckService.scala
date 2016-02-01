package com.dashcasino.service.blackjack

import com.dashcasino.dao.sql.{BlackjackCardSqlDao, BlackjackDeckSqlDao}

/**
  * Created by freezing on 1/30/16.
  */

class BlackjackDeckService(implicit val blackjackDeckSqlDao: BlackjackDeckSqlDao, blackjackCardSqlDao: BlackjackCardSqlDao) {
  type JsonString = String

}
