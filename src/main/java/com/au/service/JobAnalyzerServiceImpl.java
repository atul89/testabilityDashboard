package com.au.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JobAnalyzerServiceImpl implements AnalyzerService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public <T> T analyze(String url, Class<T> responseType) {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));         
		messageConverters.add(converter);  
		restTemplate.setMessageConverters(messageConverters);	
		return restTemplate.getForObject(url, responseType);
	}

	@Override
	public String analyze(String url) {
		return restTemplate.getForObject(url, String.class);
	}

}
