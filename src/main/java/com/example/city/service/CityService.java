package com.example.city.service;

/**
 * Service interface that provides a single method to check if 
 * two cities are connected.
 * 
 * @author anand
 *
 */
public interface CityService {
	
	public String isConnected(String src, String dest);

}
