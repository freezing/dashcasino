package com.dashcasino

import java.nio.file.Paths

/**
  * Created by freezing on 2/12/16.
  */
package object api {
  val API_MAJOR_VERSION = 1
  val API_MINOR_VERSION = 0
  val API_VERSION_PATH = Paths.get(s"api/v$API_MAJOR_VERSION")
}
