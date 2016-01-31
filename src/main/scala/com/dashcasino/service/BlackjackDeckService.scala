package com.dashcasino.service

import com.dashcasino.dao.sql.{BlackjackDeckSqlDao, BlackjackCardSqlDao}
import com.dashcasino.model._
import spray.util.NotImplementedException

import argonaut._
import Argonaut._

/**
  * Created by freezing on 1/30/16.
  */

class BlackjackDeckService(implicit val blackjackDeckSqlDao: BlackjackDeckSqlDao, blackjackCardSqlDao: BlackjackCardSqlDao) {
  type JsonString = String

}
