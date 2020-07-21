package com.example.city.data;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This is a data provider class and also has logic to determine if two cities are connected.
 * This uses a breadth first search algorithm for determining the connectivity.
 * This class assumes that city data in the format 'origin,destination' is available 
 * in the file cities.txt.
 * This is a Singleton class, please use <code>CityDataProvider.getInstance()</code> method 
 * for getting the instance.
 * 
 * @author anand
 *
 */
public class CityDataProvider {
	
	private static final String DATA_FILE = "/cities.txt";
	
    private Map<String, Set<String>> cityMap;
	
	private CityDataProvider() {
		initialize();
	}
	
	private static class SingletonHelper {
		private static final CityDataProvider INSTANCE = new CityDataProvider();
	}
	
	public static CityDataProvider getInstance() {
		return SingletonHelper.INSTANCE;
	}
	
	/*
	 * Read each line of cities.txt, split on comma and add to cityMap
	 * It assumes that for any city pair, the connectivity is bi-directional
	 */
    private void initialize() {
    	cityMap = new TreeMap<>();
    	
    	try {
    		URL url = this.getClass().getResource(DATA_FILE);
    		File input = new File(url.getPath());
    		Files.lines(input.toPath()).forEach(aLine-> {
    			String[] cities = aLine.split(",");
    			addConnection(cities[0].trim(),cities[1].trim());
    		});
    	}
    	catch(Exception e) {
    		System.err.println("An error occured while reading cities.txt");
    	}
    }
    
    
    private CityDataProvider addConnection(String src, String dest) {
    	this.addConnection(src, dest, false);
    	return this;
    }
  
    //adds city-city connection. If oneWay is false, adds reverse connection as well
    private CityDataProvider addConnection(String src, String dest, boolean oneWay)  { 
    	src = src.toLowerCase();
    	dest = dest.toLowerCase();
    	
    	Set<String> connectedCities = cityMap.get(src);
    	if(connectedCities == null) {
    		connectedCities = new HashSet<>();
    		cityMap.put(src, connectedCities);
    	}
    	
    	connectedCities.add(dest);
    	
    	if(!oneWay) {
        	connectedCities = cityMap.get(dest);
        	if(connectedCities == null) {
        		connectedCities = new HashSet<>();
        		cityMap.put(dest, connectedCities);
        	}
        	
        	connectedCities.add(src);
    		
    	}
    	return this;
    } 

    /**
     * Given a non-null origin and destination, this method checks if a 
     * direct/multi-hop connection exists between them. Returns true if 
     * connection exists or false otherwise.
     * 
     * @param src
     * @param dest
     * @return
     */
    
    public boolean isConnected(String src, String dest) 
    { 
    	if(src == null || src.isEmpty()) {
    		throw new IllegalArgumentException("Origin city cannot be null/empty");
    	}
    	
    	if(dest == null || dest.isEmpty()) {
    		throw new IllegalArgumentException("Destination city cannot be null/empty");
    	}
    	
    	// convert and use lowercase going forward to make searches case insensitive
    	src = src.toLowerCase();
    	dest = dest.toLowerCase();
    	
    	Set<String> visitedCities = new TreeSet<>();
    	
    	Queue<String> toVisitQ = new LinkedList<String>();
    	
  
        
        toVisitQ.add(src); 
  
        String currentCity = null;
        
        /*
         *  Start from origin and follow the graph represented using HashMap
         *  until a connection is found or if there are no new cities to be visited
         *  looking for connectivity.
         *  
         */
        
        
        while (toVisitQ.size() > 0) 
        { 
            // Fetch the city at top of the queue 
            currentCity = toVisitQ.poll(); 
            
            // Mark current city as 'visited'
            visitedCities.add(currentCity);

  
            String linkedCity = null;
            Set<String> cityLinks = cityMap.get(currentCity);
            
            if(cityLinks != null) {
                // Get all adjacent cities of the last visited city. 
                // If a adjacent city not been visited, then add it to 
            	// the queue for visiting
            	
            	Iterator<String> linkedCities = cityLinks.iterator();
                while (linkedCities.hasNext()) 
                { 
                    linkedCity = linkedCities.next(); 
      
                    // If this adjacent city is the desired destination, 
                    // then return true 
                    if (linkedCity.equals(dest)) 
                        return true; 
      
                    // Else, continue to do BFS 
                    if (!visitedCities.contains(linkedCity)) 
                    { 
                        visitedCities.add(linkedCity); 
                        toVisitQ.add(linkedCity);
                    } 
                    
                    
                } 
            	
            }
  
        } 
  
        // If all cities reachable from source has been traversed and no connection found, return false
        return false; 
    } 



}
