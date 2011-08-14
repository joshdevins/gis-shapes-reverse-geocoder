## Shapes Reverse Geocoder

Finding open data sets containing detailed shapefiles representing low-level geographic regions (i.e. suburbs, neighbourhoods, etc.) is exceedingly difficult. There are several companies that house this kind of data including Navteq, UrbanMapping, Yahoo!, etc. however they are hidden behind pay APIs or expensive data downloads. Furthermore, they are often simple bounding boxes and not real shapes. SimpleGeo has [recently started building](http://blog.simplegeo.com/2011/08/05/its-a-beautiful-day-in-the-neighborhood/) international neighbourhood shape files, but this is a work in progress and only covers 12 major cities. The US, Canada, UK, etc. have good local coverage from places like Zillow and various government sources, however there is still significant leg work required to assemble the data in any sensible way.

This tiny project is an attempt to bring together various shape files and data sources to provide a decent reverse geocoder down to the neighbourhood level. All of the data is "open" in that it is free for use, however in some cases it requires attribution. Living up to the licenses for all of the data used is your responsibility!

## Data Sources

* [Natural Earth](http://www.naturalearthdata.com/)
  * [Admin Level 0, countries, 10m resolution](http://www.naturalearthdata.com/http//www.naturalearthdata.com/download/10m/cultural/10m-admin-0-countries.zip) ([details](http://www.naturalearthdata.com/downloads/10m-cultural-vectors/10m-admin-0-countries/))
  * [Admin Level 1, states and provinces, 10m resolution](http://www.naturalearthdata.com/http//www.naturalearthdata.com/download/10m/cultural/10m-admin-1-states-provinces-shp.zip) ([details](http://www.naturalearthdata.com/downloads/10m-cultural-vectors/10m-admin-1-states-provinces/))
* [Flickr Shapefiles Public Dataset 2.0](http://www.flickr.com/services/shapefiles/2.0/) ([details](http://code.flickr.com/blog/2011/01/08/flickr-shapefiles-public-dataset-2-0/))
  * cities
  * city administrative regions
  * neighbourhoods, suburbs
* [Yahoo! GeoPlanet™ Data 7.6.0](http://ydn.zenfs.com/site/geo/geoplanet_data_7.6.0.zip) ([details](http://developer.yahoo.com/geo/geoplanet/data/))

## Data Licenses and Terms of Use

 * Natural Earth: Made with Natural Earth. Free vector and raster map data @ [naturalearthdata.com](http://www.naturalearthdata.com/)
 * Flickr Shapefiles Public Dataset 2.0: [Creative Commons Zero Waiver](http://creativecommons.org/publicdomain/zero/1.0/)
 * Yahoo! GeoPlanet™ Data 7.6.0: [Creative Commons Attribution](http://wiki.creativecommons.org/Creative_Commons_Attribution)
