package com.dashcasino

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User

/**
  * Created by freezing on 1/30/16.
  */
package object exception {
  class AuthorizationException(message: String) extends Exception(message)
  class CantHitException extends Exception("Can't hit in this state")
  class CantStandException extends Exception("Can't stand in this state")
  class IllegalRequestException(message: String) extends Exception(message)
  class NotEnoughMoneyException(message: String) extends Exception(message)
}
