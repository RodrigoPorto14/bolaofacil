package com.rodri.bolaofacil.tests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodri.bolaofacil.entities.User;


public class MockMvcRequests {
	
	private MediaType jsonType = MediaType.APPLICATION_JSON;
	
	private TokenUtil tokenUtil;
	
	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper;
	
	public MockMvcRequests(MockMvc mockMvc, ObjectMapper objectMapper)
	{
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
	}
	
	public MockMvcRequests(MockMvc mockMvc, ObjectMapper objectMapper, TokenUtil tokenUtil)
	{
		this(mockMvc, objectMapper);
		this.tokenUtil = tokenUtil;
	}

	public ResultActions mvcPost(Object body, String url, Object... uriVars) throws Exception
	{
		String jsonBody = objectMapper.writeValueAsString(body);
		return mockMvc.perform(post(url, uriVars)
  			.content(jsonBody)
            .contentType(jsonType)
            .accept(jsonType)
		);	
	}
	
	public ResultActions privateMvcPost(User user, Object body, String url, Object... uriVars) throws Exception
	{
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, user.getEmail(), user.getPassword());
		String jsonBody = objectMapper.writeValueAsString(body);
	
		return mockMvc.perform(post(url, uriVars)
			.header("Authorization", "Bearer " + accessToken)
  			.content(jsonBody)
            .contentType(jsonType)
            .accept(jsonType)
		);	
	}
}
