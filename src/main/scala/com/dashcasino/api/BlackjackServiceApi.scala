package com.dashcasino.api

import com.dashcasino.service.blackjack.BlackjackBet
import spray.http.MediaTypes._

import argonaut._
import Argonaut._

/**
  * Created by freezing on 2/15/16.
  */
trait BlackjackServiceApi extends ServiceApi { self: HttpApiServiceActor =>
  val BLACKJACK_PATH = API_VERSION_PATH.resolve("blackjack")

  val blackjackRoutes = {
    pathPrefix("api") {
      pathPrefix("v1") {
        pathPrefix("blackjack") {
          path("bet") {
            // TODO: Check user authorization and get userId
            val userId = -1
            parameters('blackjackDeckId, 'serverSecret, 'clientSeed, 'amount) {
              case (blackjackDeckId, serverSecret, clientSeed, amount) =>
                // TODO: With bet user sends client seed and deck is shuffled
                try {
                  blackjackDeckService.digitalSign(blackjackDeckId.toInt, serverSecret, clientSeed)

                  respondWithMediaType(`application/json`) {
                    complete {
                      blackjackService.bet(BlackjackBet(userId, blackjackDeckId.toInt, BigDecimal(amount))).asJson
                    }
                  }
                } catch {
                  case e: Exception =>
                  respondWithMediaType(`application/json`) {
                    complete {
                      // TODO: Have message type or something
                      e.getMessage.asJson
                    }
                  }
                }
            }
          }
        }
      }
    }
  }
}
