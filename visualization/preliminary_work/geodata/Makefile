counties.zip:
	curl -o counties.zip 'http://www2.census.gov/geo/tiger/GENZ2010/gz_2010_us_050_00_20m.zip'

gz_2010_us_050_00_20m.shp: counties.zip
	unzip counties.zip
	touch gz_2010_us_050_00_20m.shp

counties.json: gz_2010_us_050_00_20m.shp
	topojson -o counties.json -- counties=gz_2010_us_050_00_20m.shp
