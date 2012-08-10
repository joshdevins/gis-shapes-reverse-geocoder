# Shapes Reverse Geocoder

Finding open data sets containing detailed shapefiles representing low-level geographic regions (i.e. suburbs, neighbourhoods, etc.) is exceedingly difficult. There are several companies that house this kind of data including Navteq, UrbanMapping, Yahoo!, etc. however they are hidden behind pay APIs or expensive data downloads. Furthermore, they are often simple bounding boxes and not real shapes. SimpleGeo has [recently started building](http://blog.simplegeo.com/2011/08/05/its-a-beautiful-day-in-the-neighborhood/) international neighbourhood shape files, but this is a work in progress and only covers 12 major cities. The US, Canada, UK, etc. have good local coverage from places like [Zillow](http://www.zillow.com/howto/api/neighborhood-boundaries.htm) and various government sources, however there is still significant leg work required to assemble the data in any sensible way.

This tiny project is an attempt to bring together various shapefiles and data sources to provide a decent reverse geocoder down to the neighbourhood level. All of the data is "open" in that it is free for use, however in some cases it requires attribution. Living up to the licenses for all of the data used is your responsibility!

## Requirements

To download and build the datasets:

* bash
* wget
* unzip
* tar
* [GDAL](http://www.gdal.org) for [ogr2ogr](http://www.gdal.org/ogr2ogr.html)

To build and run the service:

* Java
* [Scala](http://www.scala-lang.org)
* [SBT](https://github.com/harrah/xsbt)

Optional:

* [QGIS](http://www.qgis.org) to view the shapefiles being used

## Getting Started

* download raw data: `sbin/downloadData.sh`
* build data to be used by the indexer: `sbin/buildData.sh`
* build services: sbt
* run service: TBD

## Data Sources

* [Natural Earth](http://www.naturalearthdata.com/)
  * [Admin Level 0, countries, 10m resolution](http://www.naturalearthdata.com/http//www.naturalearthdata.com/download/10m/cultural/10m-admin-0-countries.zip) ([details](http://www.naturalearthdata.com/downloads/10m-cultural-vectors/10m-admin-0-countries/))
  * [Admin Level 1, states and provinces, 10m resolution](http://www.naturalearthdata.com/http//www.naturalearthdata.com/download/10m/cultural/10m-admin-1-states-provinces-shp.zip) ([details](http://www.naturalearthdata.com/downloads/10m-cultural-vectors/10m-admin-1-states-provinces/))
* [Flickr Shapefiles Public Dataset 2.0](http://www.flickr.com/services/shapefiles/2.0/) ([details](http://code.flickr.com/blog/2011/01/08/flickr-shapefiles-public-dataset-2-0/))
  * counties (Admin Level 2)
  * locality (city/town)
  * neighbourhoods
* [SimpleGeo International Neighbourhoods 2011-08-04](http://s3.amazonaws.com/simplegeo-public/neighborhoods_dump_20110804.zip) ([details](http://blog.simplegeo.com/2011/08/05/its-a-beautiful-day-in-the-neighborhood/))
* [Yahoo! GeoPlanet™ Data 7.6.0](http://ydn.zenfs.com/site/geo/geoplanet_data_7.6.0.zip) ([details](http://developer.yahoo.com/geo/geoplanet/data/))

## Data Licenses and Terms of Use

 * Natural Earth: Made with Natural Earth. Free vector and raster map data @ [naturalearthdata.com](http://www.naturalearthdata.com/)
 * Flickr Shapefiles Public Dataset 2.0: [Creative Commons Zero Waiver](http://creativecommons.org/publicdomain/zero/1.0/)
 * SimpleGeo International Neighbourhoods: [Open Database License (ODbL)](http://opendatacommons.org/licenses/odbl/)
 * Yahoo! GeoPlanet™ Data 7.6.0: [Creative Commons Attribution](http://wiki.creativecommons.org/Creative_Commons_Attribution)

## License

Copyright (C) 2012 Josh Devins

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
