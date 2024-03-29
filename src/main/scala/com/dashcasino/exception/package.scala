package com.dashcasino

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User

import scala.util.{Failure, Success}

/**
  * Created by freezing on 1/30/16.
  */
package object exception {
  class AuthorizationException(message: String) extends Exception(message)
  class CantHitException extends Exception("Can't hit in this state")
  class CantStandException extends Exception("Can't stand in this state")
  class IllegalRequestException(message: String) extends Exception(message)
  class NotEnoughMoneyException(message: String) extends Exception(message)
  class InvalidServerSecretException extends Exception
  class CantDoubleDownException extends Exception("Can't doubledown in this state")
  class CantSplitException extends Exception("Can't split in this state")
}
