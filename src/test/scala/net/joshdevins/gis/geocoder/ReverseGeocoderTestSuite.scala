package net.joshdevins.gis.geocoder

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ReverseGeocoderTestSuite extends FunSuite with ShouldMatchers {

  test("basic indexing of all the shapefiles") {
    val reverseGeocoder = new ReverseGeocoder("build/data")

    System.out.println(reverseGeocoder.mkString)
  }
}
