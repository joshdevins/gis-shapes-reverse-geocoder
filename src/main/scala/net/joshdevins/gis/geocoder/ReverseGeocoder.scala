package net.joshdevins.gis.geocoder

import scala.collection._
import com.weiglewilczek.slf4s.Logging

/**
 * Encapsulates the concept of a reverse geocoder containing multiple, hierarchical shapefile indices. Indices are search from lowest-level
 * to highest-level and the results returned are the index name and the WOEID's that match a given lat-lon.
 *
 * @author Josh Devins
 */
class ReverseGeocoder(private val dataDirecotory: String) extends Logging {

  /**
   * Load all the indices that we know about in order. This list is hardcoded for now. Should likely be a tree of sorts?
   */
  private final val indices = List(
    new ShapefileIndex("neighbourhood", "data/build/flickr-shapes/neighbourhoods.shp"),
    new ShapefileIndex("county", "data/build/flickr-shapes/counties.shp"),
    new ShapefileIndex("admin-1", "data/build/naturalearth-admin-1/10m_admin_1_states_provinces_shp.shp"),
    new ShapefileIndex("admin-0", "data/build/naturalearth-admin-0/10m_admin_0_countries.shp"))

  def mkString: String = indices.mkString

  /**
   * Gets the lowest-level WOEIDs for the shapes that contain the lat/lon provided. A subsequent lookup of the WOEID's will find the point
   * hierarchy.
   */
  def getLowestLevelWOEIDSetForCoordinates(lat: Double, lon: Double): Option[(String, immutable.Set[Long])] = {

    // search in order, return first set we find
    for (index <- indices) {

      val result = index.getWOEIDSetForCoordinates(lat, lon)

      // if something is found, return it, otherwise move on to next index
      if (!result.isEmpty) {
        return new Some((index.name, result.get))
      }
    }

    return None
  }
}
