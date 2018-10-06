package com.github.util;

import org.apache.http.client.HttpClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;


@Configuration
public class RestTemplateConfig {

	@Resource private HttpClient httpClient;
	@Resource private RestTemplateBuilder restTemplateBuilder;

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = restTemplateBuilder
				.build();
		restTemplate.setRequestFactory(clientHttpRequestFactory());
		return restTemplate;
	}

	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setHttpClient(httpClient);
		return clientHttpRequestFactory;
	}

}

