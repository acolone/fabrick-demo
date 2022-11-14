package it.iad.demofabrick.config;

/**
 * @author acolone
 * 
 */

import java.net.InetSocketAddress;
import java.net.Proxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfiguration {

	@Value("${demofabrick.http.proxy.url}")
    private String proxyUrl;

    @Value("${demofabrick.http.proxy.port}")
    private int proxyPort;

    @Value("${demofabrick.http.proxy.timeout}")
    private int proxyTimeout;
    
    @Value("${demofabrick.http.useproxy}")
    private String useProxy;
    
    @Value("${demofabrick.api-key}")
    private String apiKey;
	
	@Value("${demofabrick.auth-schema}")
    private String authSchema;
	
	
    @Bean
    public RestTemplate restTemplate() {
    	if(useProxy.equals("false")){
    		return new RestTemplate();
		}else{
	        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
	        InetSocketAddress address = new InetSocketAddress(proxyUrl, proxyPort);
	        Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
	        factory.setProxy(proxy);
	        factory.setConnectTimeout(proxyTimeout);
	        factory.setReadTimeout(proxyTimeout);
	        return new RestTemplate(factory);
		}
    }
    
    @Bean
    public HttpHeaders headers() {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
    	headers.add("api-key", apiKey);
        headers.add("Auth-Schema", authSchema);
        headers.add("x-Time-Zone","Europe/Rome");
        return headers;
    }
}
