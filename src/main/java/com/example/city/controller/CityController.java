package com.example.city.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.city.service.CityService;

/**
 * Controller class with endpoint URI mapped to '/connected'
 * Returns 'yes' if origin and destination cities are connected, 'no' otherwise
 * 
 * Returns 'no' if origin and/or destination is null/empty
 * 
 * @author anand
 *
 */
@RestController
public class CityController {
	
	private CityService cityService;
	
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}
	
	@GetMapping(path = "/connected")
	public String connected(@RequestParam(required = false) String origin, @RequestParam(required = false) String destination) {
		
		if((origin == null || origin.isEmpty()) || (destination == null || destination.isEmpty()) ) {
			return "no";
		}
		
		String retVal = cityService.isConnected(origin, destination);
		return retVal;
	}
}
