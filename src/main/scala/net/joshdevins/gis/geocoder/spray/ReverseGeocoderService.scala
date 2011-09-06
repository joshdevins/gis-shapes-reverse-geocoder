package net.joshdevins.gis.geocoder.spray

import cc.spray._
import cc.spray.directives.DoubleNumber
import cc.spray.marshalling.SprayJsonMarshalling._
import cc.spray.json._
import cc.spray.json.DefaultJsonProtocol._
import net.joshdevins.gis.geocoder.ShapefileIndex

trait ReverseGeocoderService extends Directives {

  val neighbourhoods = new ShapefileIndex("neighbourhoods", "data/build/flickr-shapes/neighbourhoods.shp")

  val reverseGeocoderService = {

    path(DoubleNumber ~ "," ~ DoubleNumber) { (lat, lon) =>
      get {

        val woeidSet = neighbourhoods.getWOEIDSetForCoordinates(lat, lon)
        _.complete(woeidSet.toList.toJson)
      }
    }
  }

}
