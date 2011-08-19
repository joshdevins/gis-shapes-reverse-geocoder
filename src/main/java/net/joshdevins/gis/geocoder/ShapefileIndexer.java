package net.joshdevins.gis.geocoder;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.opengis.feature.simple.SimpleFeature;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.index.strtree.STRtree;

public final class ShapefileIndexer {

    private final STRtree index;

    private final GeometryFactory geometryFactory;

    public ShapefileIndexer(final String shapefile) throws IOException {

        System.out.println("Indexing: " + shapefile);

        File file = new File(shapefile);
        ShapefileDataStore store = new ShapefileDataStore(file.toURI().toURL());

        String name = store.getTypeNames()[0];
        SimpleFeatureSource source = store.getFeatureSource(name);
        SimpleFeatureIterator features = source.getFeatures().features();

        index = new STRtree();

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

        geometryFactory = new GeometryFactory();
    }

    public Set<Long> getWOEIDSetForCoordinates(final double lat, final double lon) {

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
