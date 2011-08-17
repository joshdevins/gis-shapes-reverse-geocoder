package net.joshdevins.gis.geocoder;

import java.io.File;
import java.util.Arrays;
import java.util.Set;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.junit.Assert;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;

import com.vividsolutions.jts.geom.Geometry;

public class ShapefileIndexerTest {

    @Test
    public void testBasicIndexing() throws Exception {

        ShapefileIndexer indexer = new ShapefileIndexer("data/build/flickr-shapes/neighbourhoods.shp");

        Assert.assertTrue(indexer.getWOEIDSetForCoordinates(0, 0).isEmpty());

        Set<Long> woeids = indexer.getWOEIDSetForCoordinates(52.5135, 13.3535);
        Assert.assertEquals(2, woeids.size());
        Assert.assertTrue(woeids.contains(675695L));
        Assert.assertTrue(woeids.contains(29352065L));
    }

    @Test
    public void testPrintShapefile() throws Exception {

        File shapefile = new File("data/build/flickr-shapes/counties.shp");
        ShapefileDataStore store = new ShapefileDataStore(shapefile.toURI().toURL());

        String name = store.getTypeNames()[0];
        System.out.println("TypeNames: " + Arrays.toString(store.getTypeNames()));

        SimpleFeatureSource source = store.getFeatureSource(name);

        // print out a feature type header
        SimpleFeatureType ft = source.getSchema();
        for (AttributeDescriptor ad : ft.getAttributeDescriptors()) {

            if (!Geometry.class.isAssignableFrom(ad.getType().getBinding())) {
                System.out.print(ad.getName() + "\t");
            }
        }

        System.out.println();

        SimpleFeatureIterator features = source.getFeatures().features();
        SimpleFeatureIterator features2 = source.getFeatures().features();

        try {
            // now print out the feature contents (every non geometric attribute)
            int i = 0;
            while (features.hasNext() && i < 10) {
                SimpleFeature feature = features.next();

                System.out.print(feature.getID() + "\t");

                for (Object attribute : feature.getAttributes()) {

                    if (!(attribute instanceof Geometry)) {
                        System.out.print(attribute + "\t");
                    }
                }

                System.out.println();
                i++;
            }

            // and finally print out every geometry in wkt format
            i = 0;
            while (features.hasNext() && i < 10) {

                SimpleFeature feature = features.next();

                System.out.print(feature.getID() + "\t");
                System.out.println(feature.getDefaultGeometry());
                i++;
            }

        } finally {
            features.close();
            features2.close();
            store.dispose();
        }
    }
}
