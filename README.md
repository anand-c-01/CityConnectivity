# CityConnectivity
Simple Rest service to check if connection exists between two cities. Relative URL for this service is '/connected'
If there is connection between 'origin' and 'destination' cities, then 'yes' is returned, else 'no'
CityService implementation class uses a singleton CityDataProvider class for loading/querying city connectivity.
CityDataProvider class reads data from cities.txt file, which contains multiple lines with 'origin,destination'
