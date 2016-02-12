package com.dashcasino.api

import akka.actor.Actor
import com.dashcasino.service.UserService

/**
  * Created by freezing on 2/12/16.
  */
class HttpApiServiceActor(implicit val userService: UserService) extends Actor with DashCasinoServiceApi {
  def actorRefFactory = context
  def receive = runRoute(routes)
}
