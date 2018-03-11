package com.github.util;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


@Configuration
public class RestTemplateConfig {

	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory() {

		ClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory() {
			@Override
			protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {

				// 支持https访问
				if (connection instanceof HttpsURLConnection) {

					HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
					TrustManager[] trustManagers = new TrustManager[]{
							new X509TrustManager() {
								// 检查客户端证书
								@Override
								public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
								}
								// 检查服务器端证书
								@Override
								public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
								}
								// 返回受信任的X509证书数组
								@Override
								public X509Certificate[] getAcceptedIssuers() {
									return null;
								}
							}
					};

					try {
						SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
						sslContext.init(null, trustManagers, new SecureRandom());
						httpsConnection.setSSLSocketFactory(sslContext.getSocketFactory());
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					super.prepareConnection(connection, httpMethod);
				}

			}
		};

		((SimpleClientHttpRequestFactory) clientHttpRequestFactory).setConnectTimeout(6 * 1000);
		((SimpleClientHttpRequestFactory) clientHttpRequestFactory).setReadTimeout(1 * 60 * 1000);

		return clientHttpRequestFactory;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder, StringHttpMessageConverter stringHttpMessageConverter) {

		RestTemplate restTemplate = builder.requestFactory(clientHttpRequestFactory()).build();
		restTemplate.getMessageConverters().set(1, stringHttpMessageConverter);

		return restTemplate;
	}

	/*@Bean("proxyRestTemplate")
	public RestTemplate proxyRestTemplate(RestTemplateBuilder builder, StringHttpMessageConverter stringHttpMessageConverter) {

		ClientHttpRequestFactory clientHttpRequestFactory = clientHttpRequestFactory();
		SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = (SimpleClientHttpRequestFactory) clientHttpRequestFactory;
		simpleClientHttpRequestFactory.setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1090)));
		RestTemplate restTemplate = builder.requestFactory(clientHttpRequestFactory).build();
		restTemplate.getMessageConverters().set(1, stringHttpMessageConverter);

		return restTemplate;
	}*/

}

