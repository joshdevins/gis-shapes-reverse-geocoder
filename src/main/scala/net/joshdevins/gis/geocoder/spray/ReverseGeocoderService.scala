package net.joshdevins.gis.geocoder.spray

import cc.spray._

trait ReverseGeocoderService extends Directives {

  val reverseGeocoderService = {
    path("") {
      get { _.complete("Say hello to Spray!") }
    }
  }

}
