package com.example.city.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CityDataProviderTest {

	private static CityDataProvider cityDataProvider;
	
	@BeforeEach
	void setUp() throws Exception {
		cityDataProvider = CityDataProvider.getInstance();
	}

	@AfterEach
	void tearDown() throws Exception {
		cityDataProvider = null;
	}

	@DisplayName("Test connected cities Edison/Newark - should return true")
	@Test
	void testConnectedCities() {
		boolean isConnected = cityDataProvider.isConnected("Edison", "Newark");
		assertEquals(true, isConnected);
	}
	
	@DisplayName("Test not connected cities Edison/Robbinsville - should return false")
	@Test
	void testNotConnectedCities() {
		boolean isConnected = cityDataProvider.isConnected("Edison", "Robbinsville");
		assertEquals(false, isConnected);
	}
	
	@DisplayName("Test multi-hop connection - Newark/Woodbridge - should return true")
	@Test
	void testMultihopConnectedCities() {
		boolean isConnected = cityDataProvider.isConnected("Newark", "Woodbridge");
		assertEquals(true, isConnected);
	}
	
	@DisplayName("Test origin is null")
	@Test
	void testOriginNull() {
		IllegalArgumentException iae;
		iae = assertThrows(IllegalArgumentException.class, 
				 									() -> {cityDataProvider.isConnected(null, "Edison");});
		assertEquals("Origin city cannot be null/empty", iae.getMessage());
		
	}
	
	@DisplayName("Test destination is null")
	@Test
	void testDestinationNull() {
		IllegalArgumentException iae;
		iae = assertThrows(IllegalArgumentException.class, 
				 									() -> {cityDataProvider.isConnected("Edison", null);});
		assertEquals("Destination city cannot be null/empty", iae.getMessage());
		
	}
	
	@DisplayName("Test non-existing origin - should return false")
	@Test
	void testNonExistingOrigin() {
		boolean isConnected = cityDataProvider.isConnected("Trenton", "Woodbridge");
		assertEquals(false, isConnected);
	}
	
	@DisplayName("Test non-existing destination - should return false")
	@Test
	void testNonExistingDestination() {
		boolean isConnected = cityDataProvider.isConnected("Woodbridge", "Camden");
		assertEquals(false, isConnected);
	}
	
	@DisplayName("Test if same instance of singleton is returned each time")
	@Test
	void testSameInstanceOfSingleton() {
		CityDataProvider provider1 = CityDataProvider.getInstance();
		CityDataProvider provider2 = CityDataProvider.getInstance();
		assertEquals(provider1.hashCode(), provider2.hashCode());
	}
	

}
