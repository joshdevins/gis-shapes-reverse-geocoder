package net.joshdevins.gis.geocoder

import scala.collection.JavaConversions._
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.io.File
import org.geotools.data.shapefile.ShapefileDataStore
import com.weiglewilczek.slf4s.Logger
import java.util.Arrays
import org.geotools.data.simple.SimpleFeatureSource
import org.opengis.feature.simple.SimpleFeatureType
import org.opengis.feature.`type`.AttributeDescriptor
import com.vividsolutions.jts.geom.Geometry
import org.geotools.data.simple.SimpleFeatureIterator
import org.opengis.feature.simple.SimpleFeature

@RunWith(classOf[JUnitRunner])
class ShapefileIndexTestSuite extends FunSuite with ShouldMatchers {

  val logger = Logger(this.getClass)

  test("basic indexing of the neighbourhoods shapefile") {

    val indexer = new ShapefileIndex("neighbourhoods", "data/build/flickr-shapes/neighbourhoods.shp");

    indexer.getWOEIDSetForCoordinates(0, 0).isEmpty should equal(true)

    val woeids = indexer.getWOEIDSetForCoordinates(52.5135, 13.3535);
    woeids.size should equal(2)
    woeids.contains(675695L) should equal(true)
    woeids.contains(29352065L) should equal(true)
  }

  test("print some details from the countries shapefile") {

    val shapefile = new File("data/build/flickr-shapes/counties.shp")
    val store = new ShapefileDataStore(shapefile.toURI.toURL)

    val name = store.getTypeNames.head
    logger.info("TypeNames: " + store.getTypeNames.iterator.mkString)

    val source: SimpleFeatureSource = store.getFeatureSource(name)

    // print out a feature type header
    val ft: SimpleFeatureType = source.getSchema
    for (ad: AttributeDescriptor <- ft.getAttributeDescriptors) {

      if (!ad.getType.getBinding.isInstanceOf[Geometry]) {
        logger.info(ad.getName + "\t")
      }
    }

    logger.info("")

    val features: SimpleFeatureIterator = source.getFeatures.features
    val features2: SimpleFeatureIterator = source.getFeatures.features

    try {
      // now print out the feature contents (every non geometric attribute)
      var i = 0
      while (features.hasNext && i < 10) {
        val feature: SimpleFeature = features.next

        logger.info(feature.getID + "\t");

        for (attribute: Object <- feature.getAttributes()) {

          if (!attribute.isInstanceOf[Geometry]) {
            logger.info(attribute + "\t")
          }
        }

        logger.info("")
        i += 1
      }

      // and finally print out every geometry in wkt format
      i = 0
      while (features.hasNext && i < 10) {

        val feature: SimpleFeature = features.next

        logger.info(feature.getID + "\t");
        logger.info(feature.getDefaultGeometry.toString);
        i += 1
      }

    } finally {
      features.close
      features2.close
      store.dispose
    }
  }
}
