package com.dashcasino.api

import com.dashcasino.model.User
import spray.http.MediaTypes._
import argonaut._
import Argonaut._

/**
  * Created by freezing on 2/12/16.
  */
trait UserServiceApi extends ServiceApi { self: HttpApiServiceActor =>
  val USER_API_PATH = API_VERSION_PATH.resolve("user")
  // TODO: Figure out nice way to use path
  val registerPath =
    pathPrefix("api") { pathPrefix("v1") { pathPrefix("user") {
      path("register") {
      parameters('email, 'password) {
        case (email, password) => respondWithMediaType(`application/json`) { complete {
          userService.registerUser(User(-1, email, password, -1)).asJson
        } } } } } }
  }

  val userRoutes = registerPath
}
