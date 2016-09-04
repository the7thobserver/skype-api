package com.skylark.the7thobserver.skype_api.main;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.skylark.the7thobserver.skype_api.objects.AuthResponse;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by Jared on 9/2/16.
 */
public class SkypeApi
{
    private RestTemplate restTemplate;
    private ObjectMapper mapper = new ObjectMapper();
    
    private AuthResponse authResponse;

    public SkypeApi(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    public AuthResponse auth(String clientId, String clientSecret) throws IOException
    {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", "client_credentials");
        map.add("scope", "https://graph.microsoft.com/.default");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        String result = restTemplate.postForObject("https://login.microsoftonline.com/common/oauth2/v2.0/token", request, String.class);
        authResponse = mapper.readValue(result, AuthResponse.class);
        
        return authResponse;
    }
    
    public void sendMessage(String conversationId, String message, String messageType)
    {
    	MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("type", messageType);
        map.add("text", message);
        
    	HttpHeaders headers = new HttpHeaders();
    	
    	System.out.println(authResponse.getTokenType());
    	System.out.println(authResponse.getAccessToken());
    	
    	headers.add("Authorization", authResponse.getTokenType() + " " + authResponse.getAccessToken());
    	
    	HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
    	
    	String result = restTemplate.postForObject("https://apis.skype.com/v3/conversations/" + conversationId + "/activities/", request, String.class);
    }
}