package com

import akka.actor.ActorSystem

import akka.util.Timeout
import scala.concurrent.duration._
/**
  * Created by freezing on 2/1/16.
  */
package object dashcasino {
  implicit val timeout = Timeout(5.seconds)
  implicit val actorSystem = ActorSystem("dash-casino-test-system")
}
