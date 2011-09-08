package net.joshdevins.gis.geocoder

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ReverseGeocoderTestSuite extends FunSuite with ShouldMatchers {

  val reverseGeocoder = new ReverseGeocoder("build/data")

  test("basic indexing of all the shapefiles") {
    reverseGeocoder.mkString should be("neighbourhood, county, admin-1, admin-0")
  }

  test("lookup lat/lon that is not in any index") {

    val option = reverseGeocoder.getLowestLevelWOEIDSetForCoordinates(0, 0)
    option.isEmpty should be(true)
  }

  test("lookup lat/lon that is only in country index") {

    val option = reverseGeocoder.getLowestLevelWOEIDSetForCoordinates(53, -125)
    option.isEmpty should be(false)
    option.get._1 should be("admin-1")

    val list = option.get._2
    list.size should be(1)
    list.head should be(0) // FIXME: this is because there are no WOEID's on these shapefiles yet
  }
}
