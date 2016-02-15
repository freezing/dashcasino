package com.dashcasino.api

import akka.actor.Actor
import com.dashcasino.service.UserService
import com.dashcasino.service.blackjack.{BlackjackDeckService, BlackjackService}

/**
  * Created by freezing on 2/12/16.
  */
class HttpApiServiceActor(implicit val userService: UserService, val blackjackService: BlackjackService, val blackjackDeckService: BlackjackDeckService) extends Actor with DashCasinoServiceApi {
  def actorRefFactory = context
  def receive = runRoute(routes)
}
