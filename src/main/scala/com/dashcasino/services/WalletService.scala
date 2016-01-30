package com.dashcasino.services

import com.mysql.jdbc.TimeUtil
import org.joda.time.DateTime

/**
  * Created by freezing on 1/30/16.
  */
class WalletService {
  // TODO: Use wallet to generate new address
  def generateDepositAddress = s"NewD_${DateTime.now().getMillis}"
}
