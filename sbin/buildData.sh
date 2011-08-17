#!/bin/bash

RAW_DIR=data/raw
BUILD_DIR=data/build

function convertFlickrGeoJSON {

  echo " $1"
  ogr2ogr -f "ESRI Shapefile" $BUILD_DIR/flickr-shapes/$1.shp $RAW_DIR/flickr-shapes/flickr_shapes_$1.geojson
}

# get back into project's root dir
if [ ! -d "sbin" ]; then
  cd ..
fi

# setup target dir
rm -rf $BUILD_DIR
mkdir -p $BUILD_DIR

# convert Flickr's geojson into shape files
echo "Converting Flickr shapes from GeoJSON into ESRI Shapefiles"
mkdir -p $BUILD_DIR/flickr-shapes

convertFlickrGeoJSON counties
convertFlickrGeoJSON regions
convertFlickrGeoJSON localities
convertFlickrGeoJSON neighbourhoods

echo "Copying Shapefiles from raw download directory"

# copy out SimpleGeo shape files
cp -R $RAW_DIR/simplegeo-international-neighbourhoods/SimpleGeo_Neighborhoods_*/shp $BUILD_DIR/simplegeo-international-neighbourhoods

# simple copy over of the others
cp -R $RAW_DIR/geoplanet $BUILD_DIR
cp -R $RAW_DIR/naturalearth-admin-0 $BUILD_DIR
cp -R $RAW_DIR/naturalearth-admin-1 $BUILD_DIR

