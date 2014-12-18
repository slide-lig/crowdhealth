**CrowdHealth**: A Social Media driven Health and Nutrition (Detection of diseases/particular nutrient consumed in a query region) framework using Geo-profiling.

The idea is to tag all geo-location enabled tweets(available publicly) with different category (say cancer,fever etc.) using text mining. We then use this learnt tags to create a beautiful visualization using the OpenStreet Map API and generate some pie-charts showing interesting disease or nutrition related statistics for the data at hand.

A small sample of the original data (10 lines each) used within the code is also present as two files:
	1) fullhealthdata.csv -- File containing the health related tweets and its metadata.
	2) fullnutritiondata.csv -- File containing the nutrition related tweets and its metadata.

The reason for this sample data is two folds:
	1) It maintains the code integrity, as anyone who wants to use the code gets it in ready to compile and execute shape.
	2) It conveys the correct format for the above mentioned files to the user, so that more data can be added in the similar format.

The 'scripts' directory contains a code snippet "uniqueCheck.py" that takes as input a file containing tweets (and other meta-data), as per the template-files (fullhealthdata.csv and fullnutritiondata.csv), and outputs the total number of distinct users in the complete file.
Usage:
	python uniqueCheck.py <path-to-file>

The 'projects' directory contains all the the Java code used for processing the file containing these tweets and serving data to the User-interface.
To configure the same please follow the following steps:
	1) cd projects
	2) mvn clean install

This will generate the 'target' directory within the 'projects' directory. Once this is done, then the user can start the dropwizard service using:
	1) ./run.sh

For More check the README within the 'projects' directory.

## API's ##
**GET Request: http://localhost/ads/tweets**

@QueryParam("minx") = Latitude South
@QueryParam("miny") = Longitude West 
@QueryParam("maxx") = Latitude North
@QueryParam("maxy") = Longitude East
@QueryParam("callback") = Javascript callback function name (since cross domain request)

**GET Request: http://localhost/ads/histograms**

Populate statistics of categories corresponding to Users, Tweets that aid in construction of different charts:
	1) Bar Charts
	2) Pie Charts
