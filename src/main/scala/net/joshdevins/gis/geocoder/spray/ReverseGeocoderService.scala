package net.joshdevins.gis.geocoder.spray

import scala.collection.JavaConversions._

import cc.spray._
import cc.spray.directives.DoubleNumber
import cc.spray.marshalling.SprayJsonMarshalling._
import cc.spray.json._
import cc.spray.json.DefaultJsonProtocol._

import net.joshdevins.gis.geocoder.ShapefileIndexer

trait ReverseGeocoderService extends Directives {

  val neighbourhoods = new ShapefileIndexer("data/build/flickr-shapes/neighbourhoods.shp")

  val reverseGeocoderService = {

    path(DoubleNumber ~ "," ~ DoubleNumber) { (lat, lon) =>
      get {

        val woeidJavaSet = neighbourhoods.getWOEIDSetForCoordinates(lat, lon)
        val woeidListJavaLong = asScalaSet(woeidJavaSet).toList
        val woeidList = woeidListJavaLong.map(x => x.longValue())

        _.complete(woeidList.toJson)
      }
    }
  }

}
