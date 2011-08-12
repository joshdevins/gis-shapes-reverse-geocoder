package net.joshdevins.gis.geocoder;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
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

        File shapefile = new File(
                "/Users/devins/Downloads/data/flickr_shapes_public_dataset_2.0/flickr_shapes_neighbourhoods.shp");
        ShapefileDataStore store = new ShapefileDataStore(shapefile.toURI().toURL());

        String name = store.getTypeNames()[0];
        SimpleFeatureSource source = store.getFeatureSource(name);
        SimpleFeatureIterator features = source.getFeatures().features();

        STRtree index = new STRtree();

        long start = new Date().getTime();
        while (features.hasNext()) {

            SimpleFeature feature = features.next();
            MultiPolygon geom = (MultiPolygon) feature.getDefaultGeometry();
            Envelope envelope = geom.getEnvelopeInternal();

            index.insert(envelope, feature);
        }

        System.out.printf("Index built in: %.2f seconds\n", (new Date().getTime() - start) / (float) 1000);

        GeometryFactory geometryFactory = new GeometryFactory();
        File output = new File(
                "/Users/devins/Downloads/data/flickr_shapes_public_dataset_2.0/flickr_shapes_neighbourhoods.txt");
        Formatter formatter = new Formatter(output);

        start = new Date().getTime();
        for (float lon = -180F; lon <= 180F; lon += 0.01F) {
            for (float lat = -90F; lat <= 90F; lat += 0.01F) {

                Coordinate coordinate = new Coordinate(lon, lat);
                Envelope searchEnv = new Envelope(coordinate);

                @SuppressWarnings("unchecked")
                List<Object> results = index.query(searchEnv);

                for (Object item : results) {

                    SimpleFeature feature = (SimpleFeature) item;
                    MultiPolygon geom = (MultiPolygon) feature.getDefaultGeometry();

                    if (geom.contains(geometryFactory.createPoint(coordinate))) {
                        formatter.format("%.2f,%.2f\t%s\n", lat, lon, feature.getAttribute("woe_id"));
                    }
                }
            }
        }

        formatter.close();

        System.out.printf("Full index query in: %d seconds\n", (new Date().getTime() - start) / 1000);
    }

    @Test
    public void testPrintShapefile() throws Exception {

        File shapefile = new File(
                "/Users/devins/Downloads/data/flickr_shapes_public_dataset_2.0/flickr_shapes_continents.shp");
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

        // now print out the feature contents (every non geometric attribute)
        SimpleFeatureIterator features = source.getFeatures().features();

        while (features.hasNext()) {
            SimpleFeature feature = features.next();

            System.out.print(feature.getID() + "\t");

            for (Object attribute : feature.getAttributes()) {

                if (!(attribute instanceof Geometry)) {
                    System.out.print(attribute + "\t");
                }
            }

            System.out.println();
        }

        // and finally print out every geometry in wkt format
        features = source.getFeatures().features();
        while (features.hasNext()) {

            SimpleFeature feature = features.next();

            System.out.print(feature.getID() + "\t");
            System.out.println(feature.getDefaultGeometry());
        }
    }
}
