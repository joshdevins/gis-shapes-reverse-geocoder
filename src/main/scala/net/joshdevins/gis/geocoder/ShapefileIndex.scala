package net.joshdevins.gis.geocoder

import com.weiglewilczek.slf4s.Logging
import com.vividsolutions.jts.index.strtree.STRtree
import com.vividsolutions.jts.geom.GeometryFactory
import java.util.Date
import java.io.File
import org.geotools.data.shapefile.ShapefileDataStore
import org.geotools.data.simple.SimpleFeatureSource
import org.opengis.feature.simple.SimpleFeature
import com.vividsolutions.jts.geom.MultiPolygon
import org.geotools.data.simple.SimpleFeatureIterator
import com.vividsolutions.jts.geom.Envelope
import com.vividsolutions.jts.geom.Coordinate
import scala.collection.JavaConversions._
import scala.collection._

/**
 * A simple wrapper around an STRtree enabling loading of a shapefile and discovery of WOEIDs of shapes for given lat/lons.
 *
 * @author Josh Devins
 */
class ShapefileIndex(val name: String, private val shapefile: String) extends Logging {

  private val index = new STRtree

  private val geometryFactory = new GeometryFactory

  /**
   * Initialize and load the shapefile into the index.
   */
  {
    logger.info("Indexing %s: %s".format(name, shapefile))
    val start = (new Date).getTime()

    val file = new File(shapefile)
    val store = new ShapefileDataStore(file.toURI.toURL)

    val typeName: String = store.getTypeNames.head
    val source: SimpleFeatureSource = store.getFeatureSource(typeName)
    val features: SimpleFeatureIterator = source.getFeatures().features()

    try {
      while (features.hasNext) {

        val feature: SimpleFeature = features.next
        val geom: MultiPolygon = feature.getDefaultGeometry.asInstanceOf[MultiPolygon]
        val envelope: Envelope = geom.getEnvelopeInternal

        index.insert(envelope, feature)
      }

    } finally {
      features.close()
      store.dispose()
    }

    val duration = (new Date).getTime() - start
    logger.info("Index built in: %.2f seconds\n".format(duration / 1000F))
  }

  /**
   * Gets the WOEIDs for the shapes that contain the lat/lon provided.
   */
  def getWOEIDSetForCoordinates(lat: Double, lon: Double): Option[immutable.Set[Long]] = {

    val coordinate = new Coordinate(lon, lat)
    val searchEnv = new Envelope(coordinate)

    val results = index.query(searchEnv)
    val rtn = new mutable.HashSet[Long]

    for (item <- results) {

      val feature = item.asInstanceOf[SimpleFeature]
      val geom = feature.getDefaultGeometry.asInstanceOf[MultiPolygon]

      if (geom.contains(geometryFactory.createPoint(coordinate))) {
        rtn += feature.getAttribute("woe_id").asInstanceOf[Long]
      }
    }

    if (rtn.isEmpty) None else { new Some(rtn.toSet[Long]) }
  }
}
