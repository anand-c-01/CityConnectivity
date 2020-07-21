package com.example.city.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.*;

import com.example.city.service.CityService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CityController.class)

class CityControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CityService cityServiceMock;
	

	@Test
	public void testConnected() throws Exception {
		
	 Mockito.when(cityServiceMock.isConnected(Mockito.anyString(),Mockito.anyString())).thenReturn("yes");
		
		RequestBuilder request = MockMvcRequestBuilders.get("/connected?origin='Edison'&destination='Newark'");
		mvc.perform(request)
		    .andExpect(content().string(containsString("yes")));
	}
	
	@Test
	public void testInvalidOrigin() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/connected?origin=&destination='Newark'");
		mvc.perform(request)
		   .andExpect((MockMvcResultMatchers.status().is2xxSuccessful()))
		   .andExpect(content().string(containsString("no")));
		
	}

	@Test
	public void testInvalidDestination() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/connected?origin=Edison");
		mvc.perform(request)
		   .andExpect((MockMvcResultMatchers.status().is2xxSuccessful()))
		   .andExpect(content().string(containsString("no")));
		
	}

}
