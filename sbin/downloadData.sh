#!/bin/bash

function downloadIfFileMissing {

  if [ ! -f $1 ]; then
    wget -c -O $1 $2
  fi
}

function unzipFile {

  directory=${1%%.*}

  rm -rf $directory
  unzip $1 -d $directory
}

function untarGzipFile {

  directory=${1%%.*}

  rm -rf $directory
  mkdir $directory
  tar -xzvf $1 -C $directory
}

# get back into project's root dir
if [ ! -d "sbin" ]; then
  cd ..
fi

# setup target dir
mkdir -p target/data/raw
cd target/data/raw

# download files only if they are missing
downloadIfFileMissing naturalearth-admin-0.zip http://www.naturalearthdata.com/http//www.naturalearthdata.com/download/10m/cultural/10m-admin-0-countries.zip
downloadIfFileMissing naturalearth-admin-1.zip http://www.naturalearthdata.com/http//www.naturalearthdata.com/download/10m/cultural/10m-admin-1-states-provinces-shp.zip
downloadIfFileMissing flickr-shapes.tar.gz http://www.flickr.com/services/shapefiles/2.0/
downloadIfFileMissing simplegeo-international-neighbourhoods.zip http://s3.amazonaws.com/simplegeo-public/neighborhoods_dump_20110804.zip
downloadIfFileMissing geoplanet.zip http://ydn.zenfs.com/site/geo/geoplanet_data_7.6.0.zip

# unpackage everything, overriding anything that already is unpackaged
unzipFile naturalearth-admin-0.zip
unzipFile naturalearth-admin-1.zip
untarGzipFile flickr-shapes.tar.gz
unzipFile simplegeo-international-neighbourhoods.zip
unzipFile geoplanet.zip

