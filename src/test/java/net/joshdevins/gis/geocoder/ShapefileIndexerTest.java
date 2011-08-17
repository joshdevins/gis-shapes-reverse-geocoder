package net.joshdevins.gis.geocoder;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.junit.Assert;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.index.strtree.STRtree;

public class ShapefileIndexerTest {

    @Test
    public void testBasicIndexing() throws Exception {

        File shapefile = new File("data/build/flickr-shapes/neighbourhoods.shp");
        ShapefileDataStore store = new ShapefileDataStore(shapefile.toURI().toURL());

        String name = store.getTypeNames()[0];
        SimpleFeatureSource source = store.getFeatureSource(name);
        SimpleFeatureIterator features = source.getFeatures().features();

        STRtree index = new STRtree();

        long start = new Date().getTime();
        try {
            while (features.hasNext()) {

                SimpleFeature feature = features.next();
                MultiPolygon geom = (MultiPolygon) feature.getDefaultGeometry();
                Envelope envelope = geom.getEnvelopeInternal();

                index.insert(envelope, feature);
            }
        } finally {
            features.close();
            store.dispose();
        }

        System.out.printf("Index built in: %.2f seconds\n", (new Date().getTime() - start) / (float) 1000);

        GeometryFactory geometryFactory = new GeometryFactory();

        Assert.assertTrue(getWOEID(0, 0, index, geometryFactory).isEmpty());

        Set<Long> woeids = getWOEID(52.5135, 13.3535, index, geometryFactory);
        Assert.assertEquals(2, woeids.size());
        Assert.assertTrue(woeids.contains(675695L));
        Assert.assertTrue(woeids.contains(29352065L));

        store.dispose();
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

    private Set<Long> getWOEID(final double lat, final double lon, final STRtree index,
            final GeometryFactory geometryFactory) {

        Coordinate coordinate = new Coordinate(lon, lat);
        Envelope searchEnv = new Envelope(coordinate);

        @SuppressWarnings("unchecked")
        List<Object> results = index.query(searchEnv);
        Set<Long> rtn = new HashSet<Long>(results.size());

        for (Object item : results) {

            SimpleFeature feature = (SimpleFeature) item;
            MultiPolygon geom = (MultiPolygon) feature.getDefaultGeometry();

            if (geom.contains(geometryFactory.createPoint(coordinate))) {
                rtn.add((Long) feature.getAttribute("woe_id"));
            }
        }

        return rtn;
    }
}
