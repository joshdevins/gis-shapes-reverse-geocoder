package net.joshdevins.gis.geocoder.spray

import scala.collection.JavaConversions._
import cc.spray._
import net.joshdevins.gis.geocoder.ShapefileIndexer
import cc.spray.directives.DoubleNumber

trait ReverseGeocoderService extends Directives {

  val reverseGeocoderService = {

    // val indexer = new ShapefileIndexer("data/build/flickr-shapes/neighbourhoods.shp")

    path(DoubleNumber ~ "," ~ DoubleNumber) { (lat, lon) =>
      get {

        //val woeidSet = indexer.getWOEIDSetForCoordinates(lat, lon)
        val woeidSet = Set(123, 456)
        _.complete(woeidSet.mkString(","))
      }
    }
  }

}
