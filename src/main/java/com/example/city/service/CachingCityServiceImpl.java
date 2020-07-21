package com.example.city.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.city.data.CityDataProvider;

/**
 * A cached implementation of <code>CityService</code>
 * 
 * @author anand
 *
 */
@Service
public class CachingCityServiceImpl implements CityService {
	
	// using a simple map for caching. This can be replaced with a caching framework
	// Also a method for purging cache entries is needed.
	private static final Map<String,String> cacheMap = new HashMap<>();
	

	@Override
	public String isConnected(String src, String dest) {
		if(src == null || dest == null) {
			throw new IllegalArgumentException("Origin and/or destination cannot be null");
		}
		
		String isConnected = cacheMap.get(src+dest);
		if(isConnected == null) {
			isConnected =  CityDataProvider.getInstance().isConnected(src, dest) ? "yes" : "no";
			cacheMap.put(src+dest, isConnected);
		}
		
		return isConnected;
	}


}
