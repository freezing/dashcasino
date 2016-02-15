package com.dashcasino.api

/**
  * Created by freezing on 2/12/16.
  */
trait DashCasinoServiceApi extends UserServiceApi with BlackjackServiceApi { self: HttpApiServiceActor =>
  val routes = userRoutes ~ blackjackRoutes
}
